package com.company;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class adminUI {
    JFrame f = new JFrame("管理员你好");
    public adminUI(JFrame lastf) {
        // TODO 自动生成的构造函数存根
        f.setSize(800, 700);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = f.getContentPane();
        c.setLayout(null);
        JButton exitBtn = new JButton("<-退出登入");
        exitBtn.setBounds(20, 10, 100, 25);
        JButton passBtn = new JButton("通过选中课程");
        passBtn.setBounds(200, 490, 150, 40);

        //
        JLabel j1 = new JLabel("未审核的课程：");
        j1.setFont(new Font("微软雅黑",Font.BOLD,20));
        j1.setForeground(Color.BLACK);
        j1.setBounds(70,70,200, 30);
        c.add(j1);

        String columnNames[] = {"课程名","课时","课程要求","课程内容","考核方式","发布老师"};
        int cnum=0;
        String sql = "select count(*) FROM course where pass=0";
        Db dbco = new Db();
        try {
            ResultSet rs1 = dbco.inquire(sql);
            if(rs1.next())
                cnum = rs1.getInt(1);
            dbco.close();
            dbco = null;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        Db dbro = new Db();
        String rowData[][] = new String[cnum][];
        sql = "select * FROM course where PASS=0";
        try {
            ResultSet rsro = dbro.inquire(sql);
            for(int i=0;i<cnum;i++)
            {
                rowData[i] = new String[6];
                rsro.next();
                rowData[i][0] = rsro.getString("name");
                rowData[i][1] = rsro.getString("time");
                rowData[i][2] = rsro.getString("ask");
                rowData[i][3] = rsro.getString("detail");
                rowData[i][4] = rsro.getString("examine");
                rowData[i][5] = rsro.getString("bywho");
            }
            dbro.close();
            dbro = null;
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

        JTable jtable = new JTable(rowData,columnNames);
        JScrollPane tablePane = new JScrollPane(jtable);
        tablePane.setBounds(50, 110, 700, 250);

        String choose[] = new String[cnum];
        for(int i=0;i<cnum;i++)
            choose[i] = rowData[i][0];
        JList<String> jlChoose = new JList<>(choose);
        jlChoose.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //可以选中任意课程
        JScrollPane jsChoose = new JScrollPane(jlChoose);
        jsChoose.setBounds(80, 400, 100, 130);

        //按钮监听
        //退出登入按钮
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                lastf.setVisible(true);
            }
        });

        //通过选中课程按钮
        passBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index=0;
                List<String> chooseValues = jlChoose.getSelectedValuesList();
                for(int i=0;i<chooseValues.size();i++)
                {
                    Db dbp = new Db();
                    String sql = "update course set pass=1 where name='";
                    sql+=chooseValues.get(i)+"'";
                    try {
                        dbp.insertORupdateORdelete(sql);	//讲pass置1
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                }
                for(int i=0;i<chooseValues.size();i++)
                {
                    Db dbc = new Db();
                    Db dbcr = new Db();
                    String id = null;
                    String sql = "select * from course where name='";
                    sql+=chooseValues.get(i)+"'";
                    try {
                        ResultSet idrs = dbc.inquire(sql);
                        if(idrs.next())
                            id = idrs.getString("bywho");
                        dbc.close();
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }


                    sql = "CREATE TABLE `java`."+chooseValues.get(i)+"_"+id+"  (\r\n" +
                            "  `num` int(0) NOT NULL AUTO_INCREMENT,\r\n" +
                            "  `name` varchar(255) NULL,\r\n" +
                            "  `age` int(0) NULL,\r\n" +
                            "  `sex` varchar(255) NULL,\r\n" +
                            "  `PROFESSION` varchar(255) NULL,\r\n" +
                            "  `class` varchar(255) NULL,\r\n" +
                            "  `STUID` int(0) NULL,\r\n" +
                            "  `email` varchar(255) NULL,\r\n" +
                            "  `phonenumber` varchar(255),\r\n" +
                            "  PRIMARY KEY (`num`)\r\n" +
                            ")";
                    try {
                        if(dbcr.insertORupdateORdelete(sql))
                        {
                            index =1;
                        }
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                    }
                }

                if(index==1) {
                    JDialog admlog = new JDialog(f,"",true);
                    admlog.setSize(300,80);
                    admlog.setLocationRelativeTo(null);
                    JLabel admjl = new JLabel("通过课程成功");
                    Container admc = admlog.getContentPane();
                    admc.setLayout(new FlowLayout());
                    JButton admbtn = new JButton("确定");
                    admbtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            admlog.setVisible(false);
                            f.setVisible(false);
                            new adminUI(lastf);
                        }
                    });
                    admc.add(admjl);
                    admc.add(admbtn);
                    admlog.setVisible(true);
                }

            }
        });

        c.add(exitBtn);c.add(tablePane);c.add(jsChoose);c.add(passBtn);
        f.setVisible(true);
    }
}
