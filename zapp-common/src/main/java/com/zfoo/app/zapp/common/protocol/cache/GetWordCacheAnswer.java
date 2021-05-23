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

package com.zfoo.app.zapp.common.protocol.cache;

import com.zfoo.app.zapp.common.protocol.cache.model.WordVO;
import com.zfoo.protocol.IPacket;

import java.util.Map;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-08-11 11:15
 */
public class GetWordCacheAnswer implements IPacket {

    public static final transient short PROTOCOL_ID = 3307;

    private Map<Long, WordVO> wordMap;

    public static GetWordCacheAnswer valueOf(Map<Long, WordVO> wordMap) {
        var ask = new GetWordCacheAnswer();
        ask.wordMap = wordMap;
        return ask;
    }

    @Override
    public short protocolId() {
        return PROTOCOL_ID;
    }

    public Map<Long, WordVO> getWordMap() {
        return wordMap;
    }

    public void setWordMap(Map<Long, WordVO> wordMap) {
        this.wordMap = wordMap;
    }
}
