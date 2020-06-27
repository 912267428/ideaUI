package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

public class Main {
    static int judge=0;
    public static void main(String[] args) throws Exception {

        JFrame f = new JFrame("配置数据库");
        f.setSize(550, 500);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = f.getContentPane();
        c.setLayout(null);

        JLabel jl1 = new JLabel("数据库账户：");
        JLabel jl2 = new JLabel("数据库密码：");
        jl1.setFont(new Font("微软雅黑",Font.PLAIN,17));
        jl1.setForeground(Color.BLACK);
        jl1.setBounds(80, 100, 120, 30);
        jl2.setFont(new Font("微软雅黑",Font.PLAIN,17));
        jl2.setForeground(Color.BLACK);
        jl2.setBounds(80, 160, 120, 30);

        JTextField idin = new JTextField(15);
        idin.setFont(new Font("微软雅黑",Font.PLAIN,17));
        idin.setBounds(200, 103, 180, 24);
        JPasswordField passin = new JPasswordField(10);
        passin.setFont(new Font("Arial",Font.BOLD,17));
        passin.setBounds(200, 164, 180, 24);

        JButton judgeBtn = new JButton("测试连接");
        judgeBtn.setBounds(90, 220, 100, 40);
        JButton sureBtn = new JButton("确定");
        sureBtn.setBounds(300, 220, 100, 40);


        judgeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                judge = 0;
                String cid = idin.getText();
                char[] chpass = passin.getPassword();
                String cpass = new String(chpass);
                if(cid.equals("")||cpass.equals(""))
                {
                    JDialog admlog = new JDialog(f,"error",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("请正确输入");
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
                    Db.USER = cid;
                    Db.PASS = cpass;
                    String sql = "select * from admin";
                    Db db = new Db();
                    try {
                        ResultSet rs =  db.inquire(sql);
                        if(rs.next())
                            judge = 1;
                        JDialog admlog = new JDialog(f,"success",true);
                        admlog.setSize(300,80);
                        admlog.setLocationRelativeTo(null);
                        JLabel admjl = new JLabel("连接成功");
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
                        db.close();
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        JDialog admlog = new JDialog(f,"error",true);
                        admlog.setSize(300,80);
                        admlog.setLocationRelativeTo(null);
                        JLabel admjl = new JLabel("连接失败");
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
            }
        });

        sureBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(judge==0)
                {
                    JDialog admlog = new JDialog(f,"error",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("请先测试连接");
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
                    f.setVisible(false);
                    Login login = new Login();
                }
            }
        });

        c.add(jl1);c.add(jl2);c.add(idin);c.add(passin);c.add(sureBtn);c.add(judgeBtn);
        f.setVisible(true);
    }
}