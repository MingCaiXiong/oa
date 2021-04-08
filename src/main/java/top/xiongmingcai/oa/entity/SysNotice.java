package top.xiongmingcai.oa.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysNotice)实体类
 *
 * @author makejava
 * @since 2021-04-08 12:23:53
 */
public class SysNotice implements Serializable {
    private static final long serialVersionUID = 605341130139796039L;
    
    private Long noticeId;
    
    private Long receiverId;
    
    private String content;
    
    private Date createTime;


    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}