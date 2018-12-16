package com.example.demo.entity;

import com.erupt.annotation.Erupt;
import com.erupt.annotation.EruptField;
import com.erupt.annotation.constant.RgbColor;
import com.erupt.annotation.sub_erupt.Card;
import com.erupt.annotation.sub_erupt.Power;
import com.erupt.annotation.sub_erupt.RowOperation;
import com.erupt.annotation.sub_erupt.Tree;
import com.erupt.annotation.sub_field.Edit;
import com.erupt.annotation.sub_field.EditType;
import com.erupt.annotation.sub_field.View;
import com.erupt.annotation.sub_field.ViewType;
import com.erupt.annotation.sub_field.sub_edit.*;
import com.erupt.model.BaseModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by liyuepeng on 9/28/18.
 */
@Erupt(
//        filter = @Filter(condition = "'id=1'"),
        name = "测试",
        power = @Power,
        rowOperation = {@RowOperation(
                code = "action",
                icon = "fa fa-terminal",
                title = "执行",
                operationHandler = OperationHandlerImpl.class
//                edits = {
//                        @CodeAndEdit(
//                                code = "name",
//                                codeType = "int",
//                                edit = @Edit(title = "姓名", notNull = true)
//                        ),
//                        @CodeAndEdit(
//                                code = "idCard",
//                                codeType = "Integer",
//                                edit = @Edit(title = "身份证号", notNull = true)
//                        )
//                }
        ), @RowOperation(code = "single", icon = "pic-left",
                title = "单个执行", multi = false,
                operationHandler = OperationHandlerImpl.class
//                edits = {
//                        @CodeAndEdit(code = "idcard", edit = @Edit(title = "身份证号")),
//                        @CodeAndEdit(code = "name", edit = @Edit(title = "姓名")),
//                }
        )
        },
        cards = {
                @Card(icon = "fa fa-table", value = "100", desc = "第一个卡片", color = RgbColor.green),
                @Card(icon = "fa fa-table", value = "200", desc = "第二个卡片", color = RgbColor.red),
                @Card(icon = "fa fa-table", value = "300", desc = "第三个卡片", color = RgbColor.blue),
                @Card(icon = "fa fa-table", value = "400", desc = "第四个卡片", color = RgbColor.yellow)
        },
        tree = @Tree(id = "id", label = "name", pid = "parent.id")
)
@Entity
@Table(name = "ERUPT_TEST")
public class Mmo extends BaseModel {

    @Column(name = "NAME")
    @EruptField(
            views = {
                    @View(title = "名称", className = "text-lime", template = "名称：@txt@", sortable = true)
            },
            edit = @Edit(
                    desc = "名称",
                    title = "名称",
                    notNull = true,
                    inputType = @InputType(length = 100),
                    search = @Search(isSearch = true)
            )
    )
    private String name;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")
    @EruptField(
            views = {
                    @View(title = "pid", column = "id")
            },
            edit = @Edit(
                    desc = "上级菜单",
                    title = "上级菜单",
                    notNull = true,
                    search = @Search(isSearch = true),
                    type = EditType.REFERENCE,
                    referenceType = @ReferenceType(id = "id", label = "name", pid = "id", depend = "name")
            )
    )
    private Mmo parent;

    @EruptField(
            edit = @Edit(
                    title = "子节点",
                    type = EditType.TAB
            )
    )
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<Mmo> children;


    @EruptField(
            views = {
                    @View(title = "number")
            },
            edit = @Edit(
                    title = "数字",
                    notNull = true,
                    search = @Search(isSearch = true)
            )
    )
    @Column(name = "AGE")
    private Integer age;

    @Column(name = "TEXT")
    @EruptField(
            views = @View(title = "选择"),
            edit = @Edit(
                    title = "选择",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = {
                            @ChoiceType(
                                    vl = {
                                            @VL(value = 123, label = "张三"),
                                            @VL(value = 234, label = "李四"),
                                            @VL(value = 456, label = "王五")
                                    },
                                    type = ChoiceEnum.SELECT_SINGLE
                            )
                    },
                    search = @Search(isSearch = true)
            )

    )
    private String choice;


    @EruptField(
            views = @View(title = "多选", viewType = ViewType.QR_CODE),
            edit = @Edit(
                    desc = "多个选择",
                    title = "多选",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = {
                            @ChoiceType(
                                    vl = {
                                            @VL(value = 123, label = "张三"),
                                            @VL(value = 234, label = "李四"),
                                            @VL(value = 456, label = "王五"),
                                            @VL(value = 2341, label = "李四"),
                                            @VL(value = 2343, label = "李四"),
                                            @VL(value = 2344, label = "李四"),
                                    },
                                    type = ChoiceEnum.SELECT_MULTI
                            )
                    },
                    search = @Search(isSearch = true)
            )
    )
    private String single;

    @EruptField(
            views = @View(title = "布尔"),
            edit = @Edit(
                    notNull = true,
                    title = "布尔",
                    type = EditType.BOOLEAN,
                    boolType = @BoolType(trueText = "是的", falseText = "不行")
            )
    )
    private Boolean bool;

    @EruptField(
            views = @View(
                    title = "时间"
            ),
            edit = @Edit(
                    notNull = true,
                    title = "时间",
                    type = EditType.DATE,
                    dateType = @DateType(type = DateEnum.WEEK),
                    group = "分组2"
            )
    )
    private Date date;

    @EruptField(
            edit = @Edit(
                    title = "图片",
                    type = EditType.IMAGE
            )
    )
    private String image;


//    @EruptField(
//            edit = @Edit(
//                    title = "tab1",
//                    type = EditType.TAB
//            )
//    )
//    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "mmo")
//    private transient Set<SubMmo> sub;


    @Column(name = "TAG")
    @EruptField(
            views = {
                    @View(title = "标签", viewType = ViewType.LINK)
            },
            edit = @Edit(
                    group = "分组2",
                    title = "名称",
                    notNull = true,
                    inputType = @InputType(type = InputEnum.TEXTAREA)
            )
    )
    private String gender;

    @Column(name = "markDown")
    @EruptField(
            edit = @Edit(
                    title = "markDown",
                    notNull = true,
                    type = EditType.MARK_DOWN
            )
    )
    public String markDown;


    @Column(name = "KISS")
    private transient String kiss;

}