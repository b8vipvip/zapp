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

package com.zfoo.app.zapp.common.entity.core;

import com.zfoo.orm.model.anno.EntityCache;
import com.zfoo.orm.model.anno.Id;
import com.zfoo.orm.model.anno.Index;
import com.zfoo.orm.model.anno.Persister;
import com.zfoo.orm.model.entity.IEntity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-08-13 16:45
 */
@EntityCache(cacheStrategy = "tenThousand", persister = @Persister("time30s"))
public class WordEditRejectEntity implements IEntity<Long> {

    @Id
    private long id;

    @Index(ascending = true, unique = false)
    private long userId;

    private int optionType;

    private List<Integer> selectedItems = new CopyOnWriteArrayList<>();

    private String content;

    private WordEntity word;

    @Override
    public Long id() {
        return id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Integer> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WordEntity getWord() {
        return word;
    }

    public void setWord(WordEntity word) {
        this.word = word;
    }
}
