/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 *
 * Contributors:
 *     vdutat
 *
 */
package org.nuxeo.ecm.automation.core.impl.adapters;

import java.util.Collection;

import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.TypeAdaptException;
import org.nuxeo.ecm.automation.TypeAdapter;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.DocumentNotFoundException;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.impl.DocumentModelListImpl;

public class ArrayListToDocumentModelList implements TypeAdapter {

    @Override
    public Object getAdaptedValue(OperationContext ctx, Object objectToAdapt) throws TypeAdaptException {
        Collection<String> list = (Collection<String>) objectToAdapt;
        DocumentModelList result = new DocumentModelListImpl();
        for (String ref : list) {
            String trim = ref.trim();
            DocumentRef docRef;
            if (ref.startsWith("/")) {
                docRef = new PathRef(trim);
            } else {
                docRef = new IdRef(trim);
            }
            try {
                result.add(ctx.getCoreSession().getDocument(docRef));
            } catch (DocumentNotFoundException e) {
                throw new TypeAdaptException(e);
            }
        }
        return result;
    }

}
