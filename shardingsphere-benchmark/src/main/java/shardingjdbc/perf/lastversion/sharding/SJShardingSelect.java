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

package shardingjdbc.perf.lastversion.sharding;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import perfstmt.ShardingPerfStmt;
import service.util.config.SJDataSourceUtil;
import service.util.config.lastversion.SJDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Sharding-JDBC select performance with version3.1.0.
 * @author nancyzrh
 */
public class SJShardingSelect extends AbstractJavaSamplerClient {
    private static final String INSERT_STMT = ShardingPerfStmt.INSERT_STMT.getValue();
    
    private static DataSource dataSource;
    
    static {
        try {
            dataSource = SJDataSourceFactory.createMSDataSource();
        } catch (final SQLException ignore) {
        }
    }
    
    /**
     * run test.
     * @param javaSamplerContext context
     * @return res
     */
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult results = new SampleResult();
        results.setSampleLabel("SJShardingSelect");
        results.sampleStart();
        try {
            SJDataSourceUtil.getSelect(INSERT_STMT, dataSource);
        } catch (SQLException ex) {
            results.setSuccessful(false);
            return results;
        } finally {
            results.sampleEnd();
        }
        results.setSuccessful(true);
        return results;
    }
}

