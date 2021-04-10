package top.xiongmingcai.oa.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysNotice)实体类
 *
 * @author makejava
 * @since 2021-04-10 18:01:07
 */
public class SysNotice implements Serializable {
    private static final long serialVersionUID = 450775096842428210L;
    
    private Long noticeId;
    
    private Long receiverId;
    
    private String content;
    
    private Date createTime;

    public SysNotice() {
    }

    public SysNotice(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
        this.createTime = new Date();
    }

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