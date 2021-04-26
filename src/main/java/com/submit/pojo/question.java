/*
 * Copyright (C) 2021 xiaoxiao(1018982338@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.submit.pojo;

/**
 * @author xiaoxiao
 * @since 2021/4/20
 */
public class question {
    /**
     * 问题标号
     */
    private Integer idquestion;

    /**
     * 作业编号
     */
    private Integer idjob;

    /**
     * 问题次序
     */
    private Integer no;
    /**
     * 问题类型
     */
    private Integer type;

    /**
     * 问题内容
     */
    private String content;

    /**
     * 问题回答
     */
    private String answer;

    /**
     * 选项个数
     */
    private Integer option;

    public Integer getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(Integer idquestion) {
        this.idquestion = idquestion;
    }

    public Integer getIdjob() {
        return idjob;
    }

    public void setIdjob(Integer idjob) {
        this.idjob = idjob;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }
}
