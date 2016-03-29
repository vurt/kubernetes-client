/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fabric8.kubernetes.client.mock.impl.donable;

import io.fabric8.kubernetes.api.builder.Function;
import io.fabric8.kubernetes.api.model.Doneable;
import io.fabric8.kubernetes.api.model.extensions.DoneableConfigMap;
import io.fabric8.kubernetes.api.model.extensions.ConfigMap;
import io.fabric8.kubernetes.api.model.extensions.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.extensions.ConfigMapFluent;
import io.fabric8.kubernetes.api.model.extensions.ConfigMapFluentImpl;
import io.fabric8.kubernetes.client.mock.MockDoneable;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

public class MockDoneableConfigMap extends ConfigMapFluentImpl<MockDoneableConfigMap> implements MockDoneable<ConfigMap> {
  private interface DelegateInterface extends Doneable<ConfigMap>, ConfigMapFluent<DoneableConfigMap> {}
  private final Function<ConfigMap, ConfigMap> visitor = new Function<ConfigMap, ConfigMap>() {
    @Override
    public ConfigMap apply(ConfigMap item) {return item;}
  };

  private final DelegateInterface delegate;

  public MockDoneableConfigMap() {
    super(new ConfigMapBuilder()
      .withNewMetadata().endMetadata()
      .build());
    this.delegate = EasyMock.createMock(DelegateInterface .class);
  }

  @Override
  public IExpectationSetters<ConfigMap> done() {
    return EasyMock.expect(delegate.done());
  }

  @Override
  public Void replay() {
    EasyMock.replay(delegate);
    return null;
  }

  @Override
  public void verify() {
    EasyMock.verify(delegate);
  }

  @Override
  public Doneable<ConfigMap> getDelegate() {
    return new DoneableConfigMap(new ConfigMapBuilder(this).build(), visitor) {
      @Override
      public ConfigMap done() {
        return delegate.done();
      }
    };
  }
}
