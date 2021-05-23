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

package com.zfoo.app.zapp.common.protocol.friend;

import com.zfoo.protocol.IPacket;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2019-11-14 10:54
 */
public class EditFriendMessageNotice implements IPacket {

    public static final transient short PROTOCOL_ID = 16004;

    private long uidA;
    private long uidB;

    private long messageId;
    private String chatMessage;

    public static EditFriendMessageNotice valueOf(long uidA, long uidB, long messageId, String chatMessage) {
        var notice = new EditFriendMessageNotice();
        notice.uidA = uidA;
        notice.uidB = uidB;
        notice.messageId = messageId;
        notice.chatMessage = chatMessage;
        return notice;
    }

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }


    public long getUidA() {
        return uidA;
    }

    public void setUidA(long uidA) {
        this.uidA = uidA;
    }

    public long getUidB() {
        return uidB;
    }

    public void setUidB(long uidB) {
        this.uidB = uidB;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
