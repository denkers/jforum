//=========================================
//  KYLE RUSSELL
//  AUT UNIVERSITY 2016
//  https://github.com/denkers/jforum
//=========================================

package com.kyleruss.jforum.ejb.entity;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="posts", catalog="jforum")
public class Posts  implements java.io.Serializable
{
     private Integer id;
     private Threads threads;
     private Users users;
     private String content;
     private Date postedDate;
     
    public Posts() 
    {
        postedDate  =   new Date();
    }
	
    public Posts(Threads threads, Users users, String content) 
    {
        this();
        this.threads    =   threads;
        this.users      =   users;
        this.content    =   content;
    }
    public Posts(Threads threads, Users users, Date postedDate, String content)
    {
       this();
       this.threads     =   threads;
       this.users       =   users;
       this.postedDate  =   postedDate;
       this.content     =   content;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() 
    {
        return this.id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="thread_id", nullable=false)
    public Threads getThreads() 
    {
        return this.threads;
    }
    
    public void setThreads(Threads threads) 
    {
        this.threads = threads;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="posted_by", nullable=false)
    public Users getUsers()
    {
        return this.users;
    }
    
    public void setUsers(Users users)
    {
        this.users = users;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="posted_date", length=19)
    public Date getPostedDate() 
    {
        return this.postedDate;
    }
    
    public void setPostedDate(Date postedDate)
    {
        this.postedDate = postedDate;
    }

    
    @Column(name="content", nullable=false, length=1500)
    public String getContent()
    {
        return this.content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getParsedContent()
    {
        Pattern pattern =   Pattern.compile("<REPLYTO>(.+?)</REPLYTO>");
        Matcher matcher =   pattern.matcher(content);
        String parsed   =   content;
        
        while(matcher.find())
        {
            String group    =   matcher.group();
            String postID   =   group.replace("<REPLYTO>", "").replace("</REPLYTO>", "");
            String injected =   group.replace(group, "<a class='label label-danger' href='#" + postID + "'><span class='glyphicon glyphicon-comment'></span> Post " + postID + "</a>");
            parsed          =   parsed.replace(group, injected);
        }
        
        return parsed;
    }
}


