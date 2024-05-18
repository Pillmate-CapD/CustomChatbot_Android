package com.example.customchatbot

class ChatFlowManager {
    private var currentStep: Int = 1

    fun getCurrentStepMessage(): String {
        return when (currentStep) {
            1 -> "'파스틱정'을 못 드시는군요. 앞으로의 알람을 끌까요?"
            2 -> "알람이 설정되었습니다."
            3 -> "알람을 끌게요"
            else -> "알 수 없는 단계입니다."
        }
    }

    fun processButtonSelection(buttonType: ButtonType): String {
        return when (currentStep) {
            1 -> {
                currentStep = if (buttonType == ButtonType.BUTTON_1) 2 else 3
                if (buttonType == ButtonType.BUTTON_1) "알람이 설정되었습니다" else "알람을 끌게요"
            }
            else -> "이전 단계에서 버튼을 선택하세요."
        }
    }
}