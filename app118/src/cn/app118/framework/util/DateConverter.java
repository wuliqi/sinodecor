/**
 * @(#)UserAction.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.framework.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.ws.security.conversation.ConversationException;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter{

	@Override
	public boolean canConvert(Class arg0) {
		return Date.class == arg0;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext arg1) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyy-MM-dd");
		try{
			calendar.setTime(dateFm.parse(reader.getValue()));
		}catch (ParseException e) {
			try {
				throw new ConversationException(e.getMessage(),e);
			} catch (ConversationException e1) {
				e1.printStackTrace();
			}
		}
		return calendar.getTime();
	}



}
