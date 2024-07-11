package com.entity.view;

import com.entity.CheliangLiuyanEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 汽车留言
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("cheliang_liuyan")
public class CheliangLiuyanView extends CheliangLiuyanEntity implements Serializable {
    private static final long serialVersionUID = 1L;




		//级联表 cheliang
			/**
			* 汽车 的 商家
			*/
			private Integer cheliangShangjiaId;
			/**
			* 汽车名称
			*/
			private String cheliangName;
			/**
			* 汽车照片
			*/
			private String cheliangPhoto;
			/**
			* 汽车品牌
			*/
			private Integer cheliangTypes;
				/**
				* 汽车品牌的值
				*/
				private String cheliangValue;
			/**
			* 价格
			*/
			private Double cheliangNewMoney;
			/**
			* 点击次数
			*/
			private Integer cheliangClicknum;
			/**
			* 赞
			*/
			private Integer zanNumber;
			/**
			* 踩
			*/
			private Integer caiNumber;
			/**
			* 汽车介绍
			*/
			private String cheliangContent;
			/**
			* 是否上架
			*/
			private Integer shangxiaTypes;
				/**
				* 是否上架的值
				*/
				private String shangxiaValue;
			/**
			* 逻辑删除
			*/
			private Integer cheliangDelete;

		//级联表 yonghu
			/**
			* 用户姓名
			*/
			private String yonghuName;
			/**
			* 用户手机号
			*/
			private String yonghuPhone;
			/**
			* 用户身份证号
			*/
			private String yonghuIdNumber;
			/**
			* 用户头像
			*/
			private String yonghuPhoto;
			/**
			* 电子邮箱
			*/
			private String yonghuEmail;

	public CheliangLiuyanView() {

	}

	public CheliangLiuyanView(CheliangLiuyanEntity cheliangLiuyanEntity) {
		try {
			BeanUtils.copyProperties(this, cheliangLiuyanEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}







				//级联表的get和set cheliang

					/**
					* 获取：汽车 的 商家
					*/
					public Integer getCheliangShangjiaId() {
						return cheliangShangjiaId;
					}
					/**
					* 设置：汽车 的 商家
					*/
					public void setCheliangShangjiaId(Integer cheliangShangjiaId) {
						this.cheliangShangjiaId = cheliangShangjiaId;
					}


					/**
					* 获取： 汽车名称
					*/
					public String getCheliangName() {
						return cheliangName;
					}
					/**
					* 设置： 汽车名称
					*/
					public void setCheliangName(String cheliangName) {
						this.cheliangName = cheliangName;
					}

					/**
					* 获取： 汽车照片
					*/
					public String getCheliangPhoto() {
						return cheliangPhoto;
					}
					/**
					* 设置： 汽车照片
					*/
					public void setCheliangPhoto(String cheliangPhoto) {
						this.cheliangPhoto = cheliangPhoto;
					}

					/**
					* 获取： 汽车品牌
					*/
					public Integer getCheliangTypes() {
						return cheliangTypes;
					}
					/**
					* 设置： 汽车品牌
					*/
					public void setCheliangTypes(Integer cheliangTypes) {
						this.cheliangTypes = cheliangTypes;
					}


						/**
						* 获取： 汽车品牌的值
						*/
						public String getCheliangValue() {
							return cheliangValue;
						}
						/**
						* 设置： 汽车品牌的值
						*/
						public void setCheliangValue(String cheliangValue) {
							this.cheliangValue = cheliangValue;
						}

					/**
					* 获取： 价格
					*/
					public Double getCheliangNewMoney() {
						return cheliangNewMoney;
					}
					/**
					* 设置： 价格
					*/
					public void setCheliangNewMoney(Double cheliangNewMoney) {
						this.cheliangNewMoney = cheliangNewMoney;
					}

					/**
					* 获取： 点击次数
					*/
					public Integer getCheliangClicknum() {
						return cheliangClicknum;
					}
					/**
					* 设置： 点击次数
					*/
					public void setCheliangClicknum(Integer cheliangClicknum) {
						this.cheliangClicknum = cheliangClicknum;
					}

					/**
					* 获取： 赞
					*/
					public Integer getZanNumber() {
						return zanNumber;
					}
					/**
					* 设置： 赞
					*/
					public void setZanNumber(Integer zanNumber) {
						this.zanNumber = zanNumber;
					}

					/**
					* 获取： 踩
					*/
					public Integer getCaiNumber() {
						return caiNumber;
					}
					/**
					* 设置： 踩
					*/
					public void setCaiNumber(Integer caiNumber) {
						this.caiNumber = caiNumber;
					}

					/**
					* 获取： 汽车介绍
					*/
					public String getCheliangContent() {
						return cheliangContent;
					}
					/**
					* 设置： 汽车介绍
					*/
					public void setCheliangContent(String cheliangContent) {
						this.cheliangContent = cheliangContent;
					}

					/**
					* 获取： 是否上架
					*/
					public Integer getShangxiaTypes() {
						return shangxiaTypes;
					}
					/**
					* 设置： 是否上架
					*/
					public void setShangxiaTypes(Integer shangxiaTypes) {
						this.shangxiaTypes = shangxiaTypes;
					}


						/**
						* 获取： 是否上架的值
						*/
						public String getShangxiaValue() {
							return shangxiaValue;
						}
						/**
						* 设置： 是否上架的值
						*/
						public void setShangxiaValue(String shangxiaValue) {
							this.shangxiaValue = shangxiaValue;
						}

					/**
					* 获取： 逻辑删除
					*/
					public Integer getCheliangDelete() {
						return cheliangDelete;
					}
					/**
					* 设置： 逻辑删除
					*/
					public void setCheliangDelete(Integer cheliangDelete) {
						this.cheliangDelete = cheliangDelete;
					}






















				//级联表的get和set yonghu

					/**
					* 获取： 用户姓名
					*/
					public String getYonghuName() {
						return yonghuName;
					}
					/**
					* 设置： 用户姓名
					*/
					public void setYonghuName(String yonghuName) {
						this.yonghuName = yonghuName;
					}

					/**
					* 获取： 用户手机号
					*/
					public String getYonghuPhone() {
						return yonghuPhone;
					}
					/**
					* 设置： 用户手机号
					*/
					public void setYonghuPhone(String yonghuPhone) {
						this.yonghuPhone = yonghuPhone;
					}

					/**
					* 获取： 用户身份证号
					*/
					public String getYonghuIdNumber() {
						return yonghuIdNumber;
					}
					/**
					* 设置： 用户身份证号
					*/
					public void setYonghuIdNumber(String yonghuIdNumber) {
						this.yonghuIdNumber = yonghuIdNumber;
					}

					/**
					* 获取： 用户头像
					*/
					public String getYonghuPhoto() {
						return yonghuPhoto;
					}
					/**
					* 设置： 用户头像
					*/
					public void setYonghuPhoto(String yonghuPhoto) {
						this.yonghuPhoto = yonghuPhoto;
					}

					/**
					* 获取： 电子邮箱
					*/
					public String getYonghuEmail() {
						return yonghuEmail;
					}
					/**
					* 设置： 电子邮箱
					*/
					public void setYonghuEmail(String yonghuEmail) {
						this.yonghuEmail = yonghuEmail;
					}



}
