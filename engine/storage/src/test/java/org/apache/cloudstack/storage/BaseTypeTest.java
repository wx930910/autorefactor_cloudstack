// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package org.apache.cloudstack.storage;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.testing.EqualsTester;

public class BaseTypeTest {
	@Test
	public void testEquals() {
		new EqualsTester().addEqualityGroup(new TestType("a"), new TestType("A")).addEqualityGroup(new TestType("Bd"), new TestType("bD"))
				.testEquals();
	}

	@Test
	public void testIsSameTypeAs() {
		Assert.assertTrue("'a' and 'A' should be considdered the same type", new TestType("a").isSameTypeAs("A"));
		Assert.assertTrue("'B' and 'b' should be considdered the same address", new TestType("B").isSameTypeAs(new TestType("b")));
	}

	@Test
	public void testEqualsWithMock() {
		new EqualsTester().addEqualityGroup(new MockTestType("a").MockedTestType, new MockTestType("A").MockedTestType)
				.addEqualityGroup(new MockTestType("Bd").MockedTestType, new MockTestType("bD").MockedTestType).testEquals();
	}

	class TestType extends BaseType {
		String content;

		public TestType(String t) {
			content = t;
		}

		@Override
		public String toString() {
			return content;
		}
	}

	class MockTestType {
		public BaseType MockedTestType;
		String content;

		public MockTestType(String t) {
			content = t;
			this.MockedTestType = Mockito.mock(BaseType.class, Mockito.CALLS_REAL_METHODS);
			mockToString();
		}

		private void mockToString() {
			Mockito.when(this.MockedTestType.toString()).thenAnswer(new Answer<Object>() {
				@Override
				public Object answer(InvocationOnMock invocation) {
					return content;
				}
			});
		}

	}

}
