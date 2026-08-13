/*
 * Created By: Pranshu Shrivastava

 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.spranshu1.common.util;

/**
 * <p>Typically used to centralize different messages used by utility to provide more information
 * to user.
 * <p>Mainly for internal use within the framework.
 */
public final class Messages {

    /**
     * The constant ERR_FILE_READ.
     */
    public static final String ERR_FILE_READ = "Error while reading file";

    /**
     * The constant ERR_FILE_NAME_MISSING.
     */
    public static final String ERR_FILE_NAME_MISSING = "please provide filepath along with filename";

    /**
     * The constant ERR_FILE_NOT_FOUND.
     */
    public static final String ERR_FILE_NOT_FOUND = "File not found at given path,"+ERR_FILE_NAME_MISSING;

    /**
     * The constant ERR_INVALID_EXT.
     */
    public static final String ERR_INVALID_EXT = "Invalid file extension.Please provide .csv or .xlsx";


    /**
     * The constant ERR_DIR_ZIP.
     */
    public static final String ERR_DIR_ZIP = "Directory could not be zipped due to exception,"+ERR_FILE_NAME_MISSING;

    /**
     * The constant ERR_FILE_ZIP.
     */
    public static final String ERR_FILE_ZIP = "File could not be zipped due to exception,"+ERR_FILE_NAME_MISSING;

    /**
     * The constant ERR_FILE_UNZIP.
     */
    public static final String ERR_FILE_UNZIP = "File could not be unzipped due to exception";

    /**
     * The constant ERR_FILE_NULL.
     */
    public static final String ERR_FILE_NULL = "File must not be null";
}