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

package com.zfoo.app.zapp.common.protocol.group.model;

import com.zfoo.protocol.IPacket;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-04-22 10:23
 */
public class GroupTimeVO implements IPacket {

    public static final transient short PROTOCOL_ID = 18014;

    private long groupId;

    private boolean mute;

    private List<ChannelTimeVO> channelTimes;

    public static GroupTimeVO valueOf(long groupId, boolean mute, List<ChannelTimeVO> channelTimes) {
        var po = new GroupTimeVO();
        po.groupId = groupId;
        po.mute = mute;
        po.channelTimes = channelTimes;
        return po;
    }

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public List<ChannelTimeVO> getChannelTimes() {
        return channelTimes;
    }

    public void setChannelTimes(List<ChannelTimeVO> channelTimes) {
        this.channelTimes = channelTimes;
    }
}
