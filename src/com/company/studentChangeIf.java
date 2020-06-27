package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class studentChangeIf {
    JDialog jd ;
    public studentChangeIf(JFrame frame,String id) {
        // TODO 自动生成的构造函数存根
        jd = new JDialog(frame,"修改信息",true);
        jd.setSize(500, 800);
        jd.setLocationRelativeTo(null);
        Container c = jd.getContentPane();
        c.setLayout(null);
        JLabel jl[] = new JLabel[11];
        jl[0] = new JLabel("用户名:");
        jl[1] = new JLabel("密码:");
        jl[2] = new JLabel("姓名:");
        jl[3] = new JLabel("年龄:");
        jl[4] = new JLabel("性别:");
        jl[5] = new JLabel("专业:");
        jl[6] = new JLabel("班级:");
        jl[7] = new JLabel("学号:");
        jl[8] = new JLabel("邮箱:");
        jl[9] = new JLabel("电话号码:");


        jl[10] = new JLabel(id);
        for(int i=0;i<10;i++)
        {
            jl[i].setFont(new Font("微软雅黑",Font.PLAIN,16));
            jl[i].setForeground(Color.BLACK);
            jl[i].setBounds(100, 160+40*i, 80, 30);
            c.add(jl[i]);
        }
        jl[10].setFont(new Font("微软雅黑",Font.PLAIN,16));
        jl[10].setForeground(Color.BLACK);
        jl[10].setBounds(200, 160, 100, 30);
        c.add(jl[10]);

        String lstr[] = new String[10];
        Db ldb = new Db();
        String lsql = "select * from student where id = '"+id+"'";
        try {
            ResultSet lrs = ldb.inquire(lsql);
            if(lrs.next())
            {
                for(int i=0;i<10;i++)
                {
                    lstr[i] = lrs.getString(i+1);
                }
            }
        } catch (Exception e2) {
            // TODO 自动生成的 catch 块
            e2.printStackTrace();
        }
        finally
        {
            try {
                ldb.close();
            } catch (Exception e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
        }
        JTextField ifin[] = new JTextField[10];
        for(int i=1;i<10;i++)
        {
            ifin[i] = new JTextField(15);
            ifin[i].setFont(new Font("微软雅黑",Font.PLAIN,18));
            ifin[i].setBounds(200, 165+40*i, 180, 24);
            ifin[i].setText(lstr[i]);
            c.add(ifin[i]);
        }
        JButton btn = new JButton("确定");
        btn.setBounds(230, 600, 100, 40);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index=1;
                String str[] = new String[10];
                for(int i=1;i<10;i++)
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
                    String sql="update student set ";
                    sql+="password='"+str[1]+"',name='"+str[2]+"',age="+str[3]+",sex='"+str[4]+"',profession='"+str[5]+"',class='"+str[6]+"',STUID='"+str[7]+"',email='"+str[8]+"', phonenumber='"+str[9]+"' where id='"+lstr[0]+"'";

                    try {
                        if(tdb.insertORupdateORdelete(sql))
                        {
                            JDialog admlog = new JDialog(jd,"",true);
                            admlog.setSize(300,80);
                            admlog.setLocationRelativeTo(null);
                            JLabel admjl = new JLabel("修改成功");
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