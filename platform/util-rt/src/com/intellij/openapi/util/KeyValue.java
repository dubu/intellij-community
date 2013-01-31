/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.openapi.util;

/**
 * Created with IntelliJ IDEA.
 * User: Irina.Chernushina
 * Date: 1/23/13
 * Time: 10:01 AM
 */
public class KeyValue<Key,Value> {
  private final Key myKey;
  private final Value myValue;

  public KeyValue(Key key, Value value) {
    myKey = key;
    myValue = value;
  }

  public static<Key, Value> KeyValue<Key, Value> create(final Key key, final Value value) {
    return new KeyValue<Key, Value>(key, value);
  }

  public Key getKey() {
    return myKey;
  }

  public Value getValue() {
    return myValue;
  }
}
