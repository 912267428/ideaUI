package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PublishCourse {
    JDialog jd ;
    public PublishCourse(JFrame frame,String id) {
        jd = new JDialog(frame,"发布课程",true);
        jd.setSize(500, 800);
        jd.setLocationRelativeTo(null);
        Container c = jd.getContentPane();
        c.setLayout(null);

        JLabel jl[] = new JLabel[5];
        jl[0] = new JLabel("课程名称:");
        jl[1] = new JLabel("课程学时:");
        jl[2] = new JLabel("课程要求:");
        jl[3] = new JLabel("课程内容:");
        jl[4] = new JLabel("考核方式:");
        for(int i=0;i<5;i++)
        {
            jl[i].setFont(new Font("微软雅黑",Font.PLAIN,16));
            jl[i].setForeground(Color.BLACK);
            jl[i].setBounds(75, 160+40*i, 100, 30);
            c.add(jl[i]);
        }
        JTextField ifin[] = new JTextField[5];
        for(int i=0;i<5;i++)
        {
            ifin[i] = new JTextField(15);
            ifin[i].setFont(new Font("微软雅黑",Font.PLAIN,18));
            ifin[i].setBounds(180, 165+40*i, 180, 24);
            c.add(ifin[i]);
        }
        JButton btn = new JButton("确定");
        btn.setBounds(230, 400, 100, 40);
        c.add(btn);
        //确定按钮监听器
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index=1;
                String str[] = new String[5];
                for(int i=0;i<5;i++)
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
                else
                {
                    Db db = new Db();
                    String sql ="insert into course(name,BYWHO,time,ASK,DETAIL,EXAMINE,PASS) VALUES('";
                    sql+=str[0]+"','"+id+"',"+str[1]+",'"+str[2]+"','"+str[3]+"','"+str[4]+"',"+"0)";
                    try {
                        if(db.insertORupdateORdelete(sql))
                        {
                            JDialog admlog = new JDialog(jd,"",true);
                            admlog.setSize(300,80);
                            admlog.setLocationRelativeTo(null);
                            JLabel admjl = new JLabel("发布成功");
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
    }
}