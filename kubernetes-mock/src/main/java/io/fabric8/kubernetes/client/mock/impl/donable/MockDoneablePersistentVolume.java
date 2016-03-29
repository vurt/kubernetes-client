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
import io.fabric8.kubernetes.api.model.DoneablePersistentVolume;
import io.fabric8.kubernetes.api.model.PersistentVolume;
import io.fabric8.kubernetes.api.model.PersistentVolumeBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeFluent;
import io.fabric8.kubernetes.api.model.PersistentVolumeFluentImpl;
import io.fabric8.kubernetes.client.mock.MockDoneable;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;

public class MockDoneablePersistentVolume extends PersistentVolumeFluentImpl<MockDoneablePersistentVolume> implements MockDoneable<PersistentVolume> {

  private interface DelegateInterface extends Doneable<PersistentVolume>, PersistentVolumeFluent<DoneablePersistentVolume> {}
  private final Function<PersistentVolume, PersistentVolume> visitor = new Function<PersistentVolume, PersistentVolume>() {
    @Override
    public PersistentVolume apply(PersistentVolume item) {return item;}
  };

  private final DelegateInterface delegate;

  public MockDoneablePersistentVolume() {
    super(new PersistentVolumeBuilder()
      .withNewMetadata().endMetadata()
      .withNewSpec().endSpec()
      .withNewStatus().endStatus()
      .build());
    this.delegate = EasyMock.createMock(DelegateInterface .class);
  }

  @Override
  public IExpectationSetters<PersistentVolume> done() {
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
  public Doneable<PersistentVolume> getDelegate() {
    return new DoneablePersistentVolume(new PersistentVolumeBuilder(this).build(), visitor) {
      @Override
      public PersistentVolume done() {
        return delegate.done();
      }
    };
  }
}
