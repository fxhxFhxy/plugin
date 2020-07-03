package com.cb.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Clinic extends Model<Clinic> {

    private static final long serialVersionUID = 1L;

    /**
     * 诊所Id
     */
    @TableId(value = "clinic_id", type = IdType.AUTO)
    private Integer clinicId;

    /**
     * 诊所名称
     */
    private String clinicName;

    /**
     * 市区id
     */
    private Integer cityId;

    private String province;

    private String city;

    /**
     * 诊所区域
     */
    private String area;

    /**
     * 诊所地址
     */
    private String clinicAdress;

    /**
     * 诊所电话
     */
    private String clinicPhone;

    /**
     * 诊所状态
     */
    private Integer clinicStatus;

    /**
     * 经营时间
     */
    private String inquirePeriod;

    /**
     * 诊所图片
     */
    private String clinicImg;

    /**
     * 诊所权限（1.春播 2.非春播）
     */
    private Integer clinicRole;

    /**
     * 库存权限（1.开启 2.非开启）
     */
    private Integer stockRole;

    /**
     * 业务经理id
     */
    private Integer adminId;

    /**
     * 审核状态（1.已审核 2.未审核）
     */
    private Integer auditStatus;

    /**
     * 是否是测试诊所（1.是 2.不是）
     */
    private Integer isTest;

    /**
     * 诊所创建时间
     */
    private LocalDateTime clinicCreateTime;

    /**
     * 评分
     */
    private BigDecimal graded;

    /**
     * 坐标级别
     */
    private Integer clinicLevel;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 诊所简介
     */
    private String clinicIntroduction;

    /**
     * 是否在线；1：是；2：否
     */
    private Integer isOnline;


    @Override
    protected Serializable pkVal() {
        return this.clinicId;
    }

}
