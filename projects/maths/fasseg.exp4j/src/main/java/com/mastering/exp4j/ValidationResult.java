/*
 * Copyright 2014 Frank Asseg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mastering.exp4j;

import java.util.List;

/**
 * Contains the validation result for a given {@link Expression}
 */
public record ValidationResult(boolean valid, List<String> errors) {

    /**
     * A static class representing a successful validation result
     */
    public static final ValidationResult SUCCESS = new ValidationResult(true, null);
}
