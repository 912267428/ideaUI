package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;


public class showCourse {
    JDialog jd ;
    public showCourse(JFrame frame,String id,String name ,String course) {
        // TODO 自动生成的构造函数存根
        Db db = new Db();
        String table = course+"_"+id;
        jd = new JDialog(frame,"选课情况",true);
        jd.setSize(800, 600);
        jd.setLocationRelativeTo(null);
        Container c = jd.getContentPane();
        c.setLayout(null);

        JLabel txt = new JLabel(course+" 选课情况");
        txt.setFont(new Font("微软雅黑",Font.BOLD,20));
        txt.setForeground(Color.BLACK);
        txt.setBounds(30, 30, 200, 30);

        //选课情况表格
        int ssum=0;
        String sql = "select count(*) from "+table;
        try {
            ResultSet rs = db.inquire(sql);
            if(rs.next())
                ssum = rs.getInt(1);
            db.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        String columnNames[] = {"序号","姓名","年龄","性别","专业","班级","学号","邮箱","手机号码"};
        String rowData[][] = new String[ssum][];
        sql = "select * from "+table;
        try {
            ResultSet  rs = db.inquire(sql);
            for(int i=0;i<ssum;i++)
            {
                rs.next();
                rowData[i] = new String[9];
                rowData[i][0] = rs.getString("num");
                rowData[i][1] = rs.getString("name");
                rowData[i][2] = rs.getString("age");
                rowData[i][3] = rs.getString("sex");
                rowData[i][4] = rs.getString("PROFESSION");
                rowData[i][5] = rs.getString("class");
                rowData[i][6] = rs.getString("STUID");
                rowData[i][7] = rs.getString("email");
                rowData[i][8] = rs.getString("phonenumber");
            }
            db.close();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        JTable jtable = new JTable(rowData,columnNames);
        JScrollPane tablePane = new JScrollPane(jtable);
        tablePane.setBounds(20, 110, 750, 250);

        JButton out = new JButton("导出课程");
        out.setBounds(400, 450, 100, 40);

        out.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OutXsl ox = new OutXsl(rowData, course);
                if(ox.output())
                {
                    JDialog admlog = new JDialog(jd,"success",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("导出成功");
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

            }
        });

        c.add(txt);c.add(out);c.add(tablePane);
    }
}