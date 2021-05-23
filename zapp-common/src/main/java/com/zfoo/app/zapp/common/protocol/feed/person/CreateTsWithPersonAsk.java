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

package com.zfoo.app.zapp.common.protocol.feed.person;

import com.zfoo.protocol.IPacket;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-03-16 13:36
 */
public class CreateTsWithPersonAsk implements IPacket {

    public static final transient short PROTOCOL_ID = 4020;

    private long personId;

    private long tsId;

    private boolean recommend;

    public static CreateTsWithPersonAsk valueOf(long personId, long tsId, boolean recommend) {
        var ask = new CreateTsWithPersonAsk();
        ask.personId = personId;
        ask.tsId = tsId;
        ask.recommend = recommend;
        return ask;
    }

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getTsId() {
        return tsId;
    }

    public void setTsId(long tsId) {
        this.tsId = tsId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
