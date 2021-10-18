package com.xzymon.nirvanasubscriber.domain;

import jdk.jfr.Timestamp;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "MSG_CONTENT")
public class TextMessageContent {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "MSG_CONTENT_SEQ")
	private Long id;

	@Version
	private Long version;

	@Timestamp
	private Date occuredTime;

	@Lob
	private String content;
}
