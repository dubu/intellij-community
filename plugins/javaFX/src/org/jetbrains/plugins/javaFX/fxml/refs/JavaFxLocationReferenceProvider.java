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
package org.jetbrains.plugins.javaFX.fxml.refs;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: anna
 */
class JavaFxLocationReferenceProvider extends PsiReferenceProvider {
  private boolean mySupportCommaInValue = false;

  JavaFxLocationReferenceProvider() {
  }

  JavaFxLocationReferenceProvider(boolean supportCommaInValue) {
    mySupportCommaInValue = supportCommaInValue;
  }

  @NotNull
  @Override
  public PsiReference[] getReferencesByElement(@NotNull final PsiElement element,
                                               @NotNull ProcessingContext context) {
    final String value = ((XmlAttributeValue)element).getValue();
    if (value.startsWith("@")) {
      return new FileReferenceSet(value.substring(1), element, 2, null, true).getAllReferences();
    }
    else {
      if (mySupportCommaInValue && value.contains(",")) {
        int startIdx = 0;
        int endIdx = 0;
        List<PsiReference> refs = new ArrayList<PsiReference>();
        while (true) {
          endIdx = value.indexOf(",", startIdx);
          Collections.addAll(refs, collectRefs(element, endIdx >= 0 ? value.substring(startIdx, endIdx) : value.substring(startIdx), startIdx + 1));
          startIdx = endIdx + 1;
          if (endIdx < 0) {
            break;
          }
        }
        return refs.toArray(new PsiReference[refs.size()]);
      } else {
        return collectRefs(element, value, 1);
      }
    }
  }

  private static PsiReference[] collectRefs(PsiElement element, String value, final int startInElement) {
    final FileReferenceSet set = new FileReferenceSet(value, element, startInElement, null, true);
    if (value.startsWith("/")) {
      set.addCustomization(FileReferenceSet.DEFAULT_PATH_EVALUATOR_OPTION, FileReferenceSet.ABSOLUTE_TOP_LEVEL);
    }
    return set.getAllReferences();
  }
}
