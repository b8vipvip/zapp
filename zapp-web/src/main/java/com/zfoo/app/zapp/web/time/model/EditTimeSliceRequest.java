/*
 * Copyright (C) 2020 The zfoo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.zfoo.app.zapp.web.time.model;

import com.zfoo.app.zapp.web.time.model.vo.CreateTimeSliceVO;

/**
 * 编辑时间片的请求
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-05-21 14:11
 */
public class EditTimeSliceRequest {

    /**
     * 时间片id
     */
    private long editId;

    /**
     * 创建时间片的内容
     */
    private CreateTimeSliceVO vo;

    public long getEditId() {
        return editId;
    }

    public void setEditId(long editId) {
        this.editId = editId;
    }

    public CreateTimeSliceVO getVo() {
        return vo;
    }

    public void setVo(CreateTimeSliceVO vo) {
        this.vo = vo;
    }
}
