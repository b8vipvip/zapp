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

import com.zfoo.app.zapp.web.time.model.vo.TimeSliceReviewVO;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2020-05-21 14:11
 */
public class ReviewTimeSliceResponse {

    private List<TimeSliceReviewVO> reviews;
    private List<TimeSliceReviewVO> edits;

    public static ReviewTimeSliceResponse valueOf(List<TimeSliceReviewVO> reviews, List<TimeSliceReviewVO> edits) {
        var response = new ReviewTimeSliceResponse();
        response.reviews = reviews;
        response.edits = edits;
        return response;
    }

    public List<TimeSliceReviewVO> getReviews() {
        return reviews;
    }

    public void setReviews(List<TimeSliceReviewVO> reviews) {
        this.reviews = reviews;
    }

    public List<TimeSliceReviewVO> getEdits() {
        return edits;
    }

    public void setEdits(List<TimeSliceReviewVO> edits) {
        this.edits = edits;
    }

}