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

package com.zfoo.app.zapp.common.protocol.group;

import com.zfoo.protocol.IPacket;

/**
 * 群组聊天
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-04-21 18:20
 */
public class DeleteGroupMessageNotice implements IPacket {

    public static final transient short PROTOCOL_ID = 19004;

    private long groupId;
    private long channelId;
    private long messageId;

    public static DeleteGroupMessageNotice valueOf(long groupId, long channelId, long messageId) {
        var notice = new DeleteGroupMessageNotice();
        notice.groupId = groupId;
        notice.channelId = channelId;
        notice.messageId = messageId;
        return notice;
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

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}