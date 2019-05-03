package com.nagarro.nagptrackingsystem.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	public int commentId;

	@ManyToOne(targetEntity = ApplicantActivity.class)
	@JoinColumn(name = "applicant_activity_id", referencedColumnName = "applicant_activity_id")
	public ApplicantActivity applicantActivity;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "owner_id", referencedColumnName = "user_id")
	public User owner;

	@Column(name = "comment")
	public String comment;

	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@Column(name = "timestamp")
	public Date timestamp;

	@Column(name = "attachment")
	public byte[] attachment;

	public Comment() {
	}

	public Comment(ApplicantActivity applicantActivity, User ownerId, String comment, Date timestamp,
			byte[] attachment) {
		this.applicantActivity = applicantActivity;
		this.owner = ownerId;
		this.comment = comment;
		this.timestamp = timestamp;
		this.attachment = attachment;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public ApplicantActivity getApplicantActivity() {
		return applicantActivity;
	}

	public void setApplicantActivity(ApplicantActivity applicantActivity) {
		this.applicantActivity = applicantActivity;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User ownerId) {
		this.owner = ownerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

}
