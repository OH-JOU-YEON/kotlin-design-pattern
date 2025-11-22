# 코틀린 디자인패턴 학습 

자주 사용되는 디자인패턴 18가지를 코틀린기반으로 해본 프로젝트입니다.

<디자인패턴의 아름다움>을 기반으로 자바 기반 디자인패턴 학습을 진행하고, 코틀린 예제 코드를 이어 학습했습니다.
어려운 부분은 인공지능의 피드백을 받았으며, 요점과 함께 주석으로 정리했습니다. 


## 프로젝트 구조 

```
kotlin-design-pattern/
├── src/
│   └── main/
│       └── kotlin/
│           ├── creational/              # 생성 패턴 
│           │   ├── singleton/          # 싱글톤
│           │   ├── factory/            # 팩토리 메서드
│           │   ├── abstractFactory/    # 추상 팩토리
│           │   ├── builder/            # 빌더
│           │   └── prototype/          # 프로토타입
│           │
│           ├── structural/              # 구조 패턴 
│           │   ├── adapter/            # 어댑터
│           │   ├── bridge/             # 브리지
│           │   ├── composite/          # 컴포지트
│           │   ├── decorator/          # 데코레이터
│           │   ├── facade/             # 퍼사드
│           │   ├── flyweight/          # 플라이웨이트
│           │   └── proxy/              # 프록시
│           │
│           └── behavioral/              # 행위 패턴 
│               ├── chainOfResponsibility/  # 책임 연쇄
│               ├── command/            # 커맨드
│               ├── iterator/           # 반복자
│               ├── mediator/           # 중재자
│               ├── memento/            # 메멘토
│               ├── observer/           # 옵저버
│               ├── state/              # 상태
│               └── strategy/           # 전략
│
└── README.md
```
