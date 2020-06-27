package com.company;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class studentUI {
    JFrame f ;
    public studentUI(JFrame lastf,String id,String name) {
        // TODO 自动生成的构造函数存根
        f = new JFrame(name +" 同学你好");
        f.setSize(800, 250);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = f.getContentPane();
        c.setLayout(null);

        JButton exitBtn = new JButton("<-退出登入");
        exitBtn.setBounds(20, 10, 100, 25);
        JButton changeif = new JButton("修改信息");
        changeif.setBounds(20, 50, 100, 30);
        JButton chooseTeacher = new JButton("选择老师");
        chooseTeacher.setBounds(400, 40, 100, 40);

        Db db = new Db();
        String sql = "select count(*) from teacher";
        //选择老师的下拉框
        int teanum=0;
        try {
            ResultSet rs =  db.inquire(sql);
            if(rs.next())
                teanum = rs.getInt(1);
            db.close();
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        sql = "select id,name from teacher";
        String teaId[]=new String[teanum];
        String teaName[]=new String[teanum];
        try {
            ResultSet rs =  db.inquire(sql);
            for(int i=0;i<teanum;i++)
            {
                rs.next();
                teaId[i] = rs.getString("id");
                teaName[i] = rs.getString("name");
            }
            db.close();
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        JComboBox<String> teabox = new JComboBox<>(teaName);
        teabox.setBounds(300, 47, 80, 25);
        teabox.setEditable(true); //编辑
        c.add(teabox);


        //按钮监听
        //退出按钮
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                lastf.setVisible(true);
            }
        });
        //修改信息
        changeif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentChangeIf tci = new studentChangeIf(f, id);
                tci.jd.setVisible(true);
            }
        });
        //选择老师按钮
        chooseTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseTeacher ct = new chooseTeacher(f,id,name,teaId[teabox.getSelectedIndex()],teaName[teabox.getSelectedIndex()]);
                ct.jd.setVisible(true);
            }
        });


        c.add(exitBtn);c.add(changeif);c.add(chooseTeacher);
        f.setVisible(true);
    }
}