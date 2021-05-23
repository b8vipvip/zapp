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

package com.zfoo.app.zapp.web.utils;

import com.zfoo.app.zapp.web.util.SliceUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-02-22 15:48
 */
public class SliceUtilsTest {

    @Test
    public void system26Test() {
        var list = List.of(1, 5000, Integer.MAX_VALUE);
        for (var n : list) {
            var n26 = SliceUtils.toNumberSystem26(n);
            var fromN26 = SliceUtils.fromNumberSystem26(n26);
            Assert.assertEquals(n.intValue(), fromN26);
        }
    }

}
