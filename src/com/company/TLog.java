package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TLog {
    JDialog jd ;
    public TLog(JFrame frame) {
        // TODO 自动生成的构造函数存根
        jd = new JDialog(frame,"注册",true);
        jd.setSize(500, 800);
        jd.setLocationRelativeTo(null);
        Container c = jd.getContentPane();
        c.setLayout(null);
        JLabel jl[] = new JLabel[7];
        jl[0] = new JLabel("用户名:");
        jl[1] = new JLabel("密码:");
        jl[2] = new JLabel("姓名:");
        jl[3] = new JLabel("年龄:");
        jl[4] = new JLabel("性别:");
        jl[5] = new JLabel("专业:");
        jl[6] = new JLabel("职称:");
        for(int i=0;i<7;i++)
        {
            jl[i].setFont(new Font("微软雅黑",Font.PLAIN,16));
            jl[i].setForeground(Color.BLACK);
            jl[i].setBounds(100, 160+40*i, 60, 30);
            c.add(jl[i]);
        }
        JTextField ifin[] = new JTextField[7];
        for(int i=0;i<7;i++)
        {
            ifin[i] = new JTextField(15);
            ifin[i].setFont(new Font("微软雅黑",Font.PLAIN,18));
            ifin[i].setBounds(180, 165+40*i, 180, 24);
            c.add(ifin[i]);
        }
        JButton btn = new JButton("确定");
        btn.setBounds(230, 500, 100, 40);
        //确定按钮的事件
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index=1;
                String str[] = new String[7];
                for(int i=0;i<7;i++)
                {
                    str[i] = ifin[i].getText();
                    if(str[i].equals(""))
                        index = 0;
                }
                if(index==0)
                {
                    JDialog admlog = new JDialog(jd,"error",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("信息不能为空");
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
                    Db tdb = new Db();
                    String sql="insert into teacher values('";
                    sql+=str[0]+"','"+str[1]+"','"+str[2]+"',"+str[3]+",'"+str[4]+"','"+str[5]+"','"+str[6]+"')";
                    try {
                        if(tdb.insertORupdateORdelete(sql))
                        {
                            JDialog admlog = new JDialog(jd,"",true);
                            admlog.setSize(300,80);
                            admlog.setLocationRelativeTo(null);
                            JLabel admjl = new JLabel("注册成功");
                            Container admc = admlog.getContentPane();
                            admc.setLayout(new FlowLayout());
                            JButton admbtn = new JButton("确定");
                            admbtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    admlog.setVisible(false);
                                    jd.setVisible(false);
                                }
                            });
                            admc.add(admjl);
                            admc.add(admbtn);
                            admlog.setVisible(true);
                        }
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                }
            }
        });

        c.add(btn);
    }
}
