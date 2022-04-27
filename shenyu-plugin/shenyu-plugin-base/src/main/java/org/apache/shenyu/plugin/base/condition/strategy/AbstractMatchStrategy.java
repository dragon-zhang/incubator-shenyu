/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.plugin.base.condition.strategy;

import org.apache.shenyu.common.cache.MemorySafeLRUMap;
import org.apache.shenyu.common.constant.Constants;
import org.apache.shenyu.common.dto.ConditionData;
import org.apache.shenyu.plugin.base.condition.data.ParameterDataFactory;
import org.apache.shenyu.plugin.base.condition.judge.PredicateJudgeFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * AbstractMatchStrategy.
 */
public abstract class AbstractMatchStrategy implements MatchStrategy {

    private static final MemorySafeLRUMap<String, ConditionData> CACHE = new MemorySafeLRUMap<>(Constants.THE_256_MB, 1 << 16);

    @Override
    public final Boolean match(final List<ConditionData> conditionDataList, final ServerWebExchange exchange) {
        List<Boolean> results = new LinkedList<>();
        for (ConditionData condition : conditionDataList) {
            final String realData = buildRealData(condition, exchange);
            final Boolean result = PredicateJudgeFactory.judge(condition, realData);
            results.add(result);
        }
        return merge(results);
    }

    @Override
    public List<ConditionData> findMatchedCondition(List<ConditionData> conditionDataList, ServerWebExchange exchange) {
        List<ConditionData> results = new LinkedList<>();
        for (ConditionData condition : conditionDataList) {
            final String realData = buildRealData(condition, exchange);
            final ConditionData conditionData = CACHE.get(realData);
            if (Objects.nonNull(conditionData)) {
                results.add(conditionData);
                continue;
            }
            final Boolean result = PredicateJudgeFactory.judge(condition, realData);
            if (!result) {
                continue;
            }
            CACHE.put(realData, condition);
            results.add(condition);
        }
        return results;
    }

    /**
     * merge match results.
     *
     * @param results the match results
     * @return true if match
     */
    protected abstract Boolean merge(List<Boolean> results);

    /**
     * Build real data string.
     *
     * @param condition the condition
     * @param exchange  the exchange
     * @return the string
     */
    public String buildRealData(final ConditionData condition, final ServerWebExchange exchange) {
        return ParameterDataFactory.builderData(condition.getParamType(), condition.getParamName(), exchange);
    }
}
