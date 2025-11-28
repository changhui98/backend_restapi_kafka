package com.gbg.sagaorchestrator.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_saga_order_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SagaState {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // 👉 어떤 주문(Order)에서 발생한 사가인지 구분하기 위한 값.
    //    하나의 orderId 안에서 여러 단계(step)의 사가 상태가 기록됨.
    private UUID orderId;

    // 👉 사가 단계 이름.
    //    예: "USER_VALIDATION", "STOCK_DECREMENT", "PAYMENT_PROCESS", "COMPENSATION" 등
    //    오케스트레이터가 현재 어느 스텝을 실행 중인지 추적하기 위해 저장함.
    private String step;

    // 👉 해당 사가 단계의 결과 상태.
    //    예: SUCCESS / FAIL / IN_PROGRESS
    //    EnumType.STRING 사용 이유: 숫자(EnumType.ORDINAL) 저장 시 enum 순서 변경하면 치명적 데이터 오염 발생.
    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    // 👉 상세 정보 또는 오류 메시지/성공 메시지/외부 서비스 응답 등 기록.
    //    예: “유저 검증 실패 - USER NOT FOUND”
    //        “재고 차감 성공 - itemId=xxx, remain=10”
    //    장애 분석·모니터링에 필수.
    private String detail;

    public static SagaState of(
        UUID orderId,
        String step,
        SagaStatus status,
        String detail
    ) {
        SagaState sagaState = new SagaState();
        sagaState.orderId = orderId;
        sagaState.step = step;
        sagaState.status = status;
        sagaState.detail = detail;
        return sagaState;
    }

}
