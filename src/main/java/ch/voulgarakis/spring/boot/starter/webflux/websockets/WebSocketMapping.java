/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.voulgarakis.spring.boot.starter.webflux.websockets;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface WebSocketMapping {

    /**
     * The path mapping URIs (e.g. "/myPath.do").
     * Ant-style path patterns are also supported (e.g. "/myPath/*.do").
     * At the method level, relative paths (e.g. "edit.do") are supported
     * within the primary mapping expressed at the type level.
     * Path mapping URIs may contain placeholders (e.g. "/${connect}").
     * <p><b>Supported at the type level as well as at the method level!</b>
     * When used at the type level, all method-level mappings inherit
     * this primary mapping, narrowing it for a specific handler method.
     *
     * @see ValueConstants#DEFAULT_NONE
     * @since 4.2
     */
    @AliasFor("value")
    String[] value() default {};

}
