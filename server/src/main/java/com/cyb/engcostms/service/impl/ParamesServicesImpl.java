package com.cyb.engcostms.service.impl;

import javax.annotation.Resource;

import com.cyb.engcostms.domain.Parames;
import com.cyb.engcostms.utils.MyUtils;
import org.springframework.stereotype.Service;
import com.cyb.engcostms.dao.ParamesMapper;
import com.cyb.engcostms.service.ParamesServices;

import java.util.List;

@Service(value="paramesServices")
public class ParamesServicesImpl implements ParamesServices {
	
	@Resource
	private ParamesMapper paramesMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return paramesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Parames record) {
		record.setId(MyUtils.getPrimaryKey());
		return paramesMapper.insert(record);
	}

	@Override
	public int insertSelective(Parames record) {
		return paramesMapper.insertSelective(record);
	}

	@Override
	public Parames selectByPrimaryKey(String id) {
		return paramesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Parames record) {
		return paramesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Parames record) {
		return paramesMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Parames> selectByParames(Parames record) {
		return paramesMapper.selectByParames(record);
	}
}
