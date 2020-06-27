package com.company;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

public class teacherUI {
    JFrame f;
    public teacherUI(JFrame lastf,String id,String name) throws Exception {
        f = new JFrame(name+" 老师您好");
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = f.getContentPane();
        c.setLayout(null);

        JButton publishBtn = new JButton("发布课程");
        publishBtn.setBounds(330, 200, 100, 40);
        JButton exitBtn = new JButton("<-退出登入");
        exitBtn.setBounds(20, 10, 100, 25);
        JButton showBtn = new JButton("查看选课");
        showBtn.setBounds(330, 320, 100, 40);
        JButton changeif = new JButton("修改信息");
        changeif.setBounds(330, 80, 100, 40);

        //将老师发布的课程放入下拉框

        int cousum=0;
        Db db = new Db();
        String sql = "select count(*) from course where bywho='"+id+"' and pass=1";
        ResultSet rs = db.inquire(sql);
        if(rs.next())
            cousum = rs.getInt(1);
        String str[] = new String[cousum];

        sql = "select `name` from course where bywho='"+id+"' and pass=1";
        rs = db.inquire(sql);
        for(int i=0;i<cousum;i++)
        {
            rs.next();
            str[i] = rs.getString("name");

        }
        db.close();
        //hh
        JComboBox<String> courseBox = new JComboBox<>(str);
        courseBox.setBounds(200, 325, 120, 30);
        courseBox.setEditable(true); //是否可以编辑

        c.add(publishBtn);c.add(exitBtn);c.add(showBtn);c.add(courseBox);c.add(changeif);

        //事件监听
        //退出按钮
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                lastf.setVisible(true);
            }
        });
        //发布课程按钮
        publishBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PublishCourse pc = new PublishCourse(f, id);
                pc.jd.setVisible(true);
            }
        });
        //查看选课按钮
        showBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String course = (String) courseBox.getSelectedItem();
                if(course==null){
                    JDialog admlog = new JDialog(f,"error",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("请选择课程");
                    Container admc = admlog.getContentPane();
                    admc.setLayout(new FlowLayout());
                    JButton admbtn = new JButton("确定");
                    admbtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            admlog.setVisible(false);
                        }
                    });
                    admc.add(admjl);
                    admc.add(admbtn);
                    admlog.setVisible(true);
                }
                else {
                    showCourse sc = new showCourse(f, id, name, course);
                    sc.jd.setVisible(true);
                }
            }
        });
        //修改信息按钮
        changeif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherChangeIf tci = new teacherChangeIf(f, id);
                tci.jd.setVisible(true);
            }
        });

        f.setVisible(true);
    }
}