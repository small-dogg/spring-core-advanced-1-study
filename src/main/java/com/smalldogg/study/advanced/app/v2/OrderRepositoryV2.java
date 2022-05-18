package com.smalldogg.study.advanced.app.v2;

import com.smalldogg.study.advanced.trace.TraceStatus;
import com.smalldogg.study.advanced.trace.hellotrace.HelloTraceV1;
import com.smalldogg.study.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {
    private final HelloTraceV2 trace;

    public void save(TraceStatus status, String itemId) {
        try {
            status = trace.beginSync(status.getTraceId(), "OrderRepositoryV2.save()");
            //저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;//예외를 꼭 다시 던져주어야 한다.
        }

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
