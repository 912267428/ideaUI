package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.*;

/*
 * 登入界面
 */
public class Login {
    JFrame f= new JFrame("登入");
    public Login() {
        // TODO 自动生成的构造函数存根
        f.setSize(500, 680);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = f.getContentPane();
        c.setLayout(null);
        JLabel ltitle = new JLabel("选课系统登入");
        ltitle.setFont(new Font("微软雅黑",Font.BOLD,30));
        ltitle.setForeground(Color.BLACK);
        ltitle.setBounds(20, 20, 500, 40);

        JLabel jid = new JLabel("账号：");
        ltitle.setFont(new Font("微软雅黑",Font.BOLD,40));
        ltitle.setForeground(Color.BLACK);
        jid.setBounds(80, 200, 50, 30);
        JLabel jpass = new JLabel("密码：");
        ltitle.setFont(new Font("微软雅黑",Font.BOLD,40));
        ltitle.setForeground(Color.BLACK);
        jpass.setBounds(80, 240, 50, 30);

        JTextField idin = new JTextField(15);
        idin.setFont(new Font("微软雅黑",Font.PLAIN,18));
        idin.setBounds(130, 205, 180, 24);
        JPasswordField passin = new JPasswordField(6);
        passin.setFont(new Font("Arial",Font.BOLD,18));
        passin.setBounds(130, 245, 180, 24);

        String str[] = {"管理员","教师","学生"};
        JComboBox<String> choose = new JComboBox<>(str);
        choose.setBounds(135, 300, 100, 30);

        JButton loginbtn = new JButton("登入");
        JButton logbtn = new JButton("注册");
        loginbtn.setBounds(115, 350, 100, 40);
        logbtn.setBounds(245, 350, 100, 40);
        //添加
        c.add(ltitle);
        c.add(jid);
        c.add(jpass);
        c.add(idin);
        c.add(passin);
        c.add(choose);
        c.add(loginbtn);
        c.add(logbtn);

        //事件
        //注册按钮
        logbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(choose.getSelectedItem().equals("管理员"))
                {
                    JDialog admlog = new JDialog(f,"注册",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("管理员不能注册");
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
                if(choose.getSelectedItem().equals("教师"))
                {
                    TLog tlog= new TLog(f);
                    tlog.jd.setVisible(true);
                }
                if(choose.getSelectedItem().equals("学生"))
                {
                    SLog slog= new SLog(f);
                    slog.jd.setVisible(true);
                }
            }
        });

        //登入按钮
        loginbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id=null,uidin;
                String pass=null,upassin;
                String name =null;
                char[] chpass;
                uidin = idin.getText();
                chpass = passin.getPassword();
                upassin = new String(chpass);
                if(choose.getSelectedItem().equals("教师"))
                {
                    String selectTsql = "select `id`,`password`,`name` from teacher";

                    Db db = new Db();
                    int index=0;
                    try {
                        ResultSet rs = db.inquire(selectTsql);
                        while(rs.next())
                        {
                            id = rs.getString("id");
                            pass = rs.getString("password");
                            if(id.equals(uidin)&&pass.equals(upassin))
                            {
                                index = 1;
                                name = rs.getString("name");
                                break;
                            }
                        }
                        db.close();
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                    if(index == 0)
                    {
                        JDialog ma = new JDialog(f,"",true);
                        ma.setSize(300,80);
                        ma.setLocationRelativeTo(null);
                        JLabel admjl = new JLabel("账户或密码错误");
                        Container admc = ma.getContentPane();
                        admc.setLayout(new FlowLayout());
                        JButton admbtn = new JButton("确定");
                        admbtn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ma.setVisible(false);
                            }
                        });
                        admc.add(admjl);
                        admc.add(admbtn);
                        ma.setVisible(true);
                    }
                    else {
                        f.setVisible(false);
                        //调出教师界面
                        try {
                            teacherUI teacher = new teacherUI(f,uidin, name);
                        } catch (Exception e1) {
                            // TODO 自动生成的 catch 块
                            e1.printStackTrace();
                        }
                    }
                }
                if(choose.getSelectedItem().equals("管理员"))
                {
                    String selectTsql = "select `id`,`password` from admin";
                    Db db = new Db();
                    int index=0;
                    try {
                        ResultSet rs = db.inquire(selectTsql);
                        while(rs.next())
                        {
                            id = rs.getString("id");
                            pass = rs.getString("password");
                            if(id.equals(uidin)&&pass.equals(upassin))
                            {
                                index = 1;
                                break;
                            }
                        }
                        db.close();
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                    if(index == 0)
                    {
                        JDialog ma = new JDialog(f,"",true);
                        ma.setSize(300,80);
                        ma.setLocationRelativeTo(null);
                        JLabel admjl = new JLabel("账户或密码错误");
                        Container admc = ma.getContentPane();
                        admc.setLayout(new FlowLayout());
                        JButton admbtn = new JButton("确定");
                        admbtn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ma.setVisible(false);
                            }
                        });
                        admc.add(admjl);
                        admc.add(admbtn);
                        ma.setVisible(true);
                    }
                    else {
                        f.setVisible(false);
                        //调出管理员界面
                        adminUI admin = new adminUI(f);
                    }
                }
                if(choose.getSelectedItem().equals("学生"))
                {
                    String selectTsql = "select `id`,`password`,`name` from student";
                    Db db = new Db();
                    int index=0;
                    try {
                        ResultSet rs = db.inquire(selectTsql);
                        while(rs.next())
                        {
                            id = rs.getString("id");
                            pass = rs.getString("password");
                            name = rs.getString("name");
                            if(id.equals(uidin)&&pass.equals(upassin))
                            {
                                index = 1;
                                break;
                            }
                        }
                        db.close();
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                    if(index == 0)
                    {
                        JDialog ma = new JDialog(f,"",true);
                        ma.setSize(300,80);
                        ma.setLocationRelativeTo(null);
                        JLabel admjl = new JLabel("账户或密码错误");
                        Container admc = ma.getContentPane();
                        admc.setLayout(new FlowLayout());
                        JButton admbtn = new JButton("确定");
                        admbtn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                ma.setVisible(false);
                            }
                        });
                        admc.add(admjl);
                        admc.add(admbtn);
                        ma.setVisible(true);
                    }
                    else {
                        f.setVisible(false);
                        //调出学生界面
                        studentUI student = new studentUI(f,id,name);
                    }
                }
            }
        });

        f.setVisible(true);


    }

}