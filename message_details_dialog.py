from typing import Any, Dict, Optional

from PyQt6 import QtCore, QtGui, QtWidgets


class MessageDetailsDialog(QtWidgets.QDialog):
    """Modal dialog to present message details in a readable layout."""

    def __init__(
        self,
        parent: Optional[QtWidgets.QWidget] = None,
        message: Optional[Dict[str, Any]] = None,
        window_title: Optional[str] = None,
    ) -> None:
        super().__init__(parent)
        self.setObjectName("message_details_dialog")
        self.setModal(True)
        self.setWindowFlag(QtCore.Qt.WindowType.WindowContextHelpButtonHint, False)

        self._message: Dict[str, Any] = message or {}

        title_from_type = str(self._message.get("type", "Message")).title()
        self.setWindowTitle(window_title or f"{title_from_type} Details")

        self._build_ui()
        self._populate_fields()

    def _build_ui(self) -> None:
        self.setMinimumSize(560, 420)
        self.setStyleSheet(
            """
            QDialog#message_details_dialog {
                background-color: white;
                border-radius: 12px;
            }
            QLabel#sectionTitle {
                font-size: 16px;
                font-weight: 600;
            }
            QLabel[variant="fieldLabel"] {
                color: #555;
            }
            QTextEdit#contentEditor {
                background-color: #f7f7f9;
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                padding: 8px;
            }
            QFrame#card {
                border: 1px solid #e0e0e0;
                border-radius: 10px;
                background: #fff;
            }
            QPushButton#primaryButton {
                background-color: #084924;
                color: white;
                border-radius: 6px;
                padding: 6px 12px;
            }
            QPushButton#primaryButton:hover { background-color: #0a5a2e; }
            QPushButton#primaryButton:pressed { background-color: #06381b; }
            """
        )

        main_layout = QtWidgets.QVBoxLayout(self)
        main_layout.setContentsMargins(16, 16, 16, 16)
        main_layout.setSpacing(12)

        header_label = QtWidgets.QLabel("Message Details", self)
        header_label.setObjectName("sectionTitle")
        main_layout.addWidget(header_label)

        card = QtWidgets.QFrame(self)
        card.setObjectName("card")
        card_layout = QtWidgets.QGridLayout(card)
        card_layout.setContentsMargins(12, 12, 12, 12)
        card_layout.setHorizontalSpacing(18)
        card_layout.setVerticalSpacing(10)

        self.title_value = QtWidgets.QLabel("", card)
        self.sender_value = QtWidgets.QLabel("", card)
        self.priority_value = QtWidgets.QLabel("", card)
        self.status_value = QtWidgets.QLabel("", card)

        def lbl(text: str) -> QtWidgets.QLabel:
            lab = QtWidgets.QLabel(text, card)
            lab.setProperty("variant", "fieldLabel")
            return lab

        row = 0
        card_layout.addWidget(lbl("Title"), row, 0)
        card_layout.addWidget(self.title_value, row, 1)
        row += 1
        card_layout.addWidget(lbl("From"), row, 0)
        card_layout.addWidget(self.sender_value, row, 1)
        row += 1
        card_layout.addWidget(lbl("Priority"), row, 0)
        card_layout.addWidget(self.priority_value, row, 1)
        row += 1
        card_layout.addWidget(lbl("Status"), row, 0)
        card_layout.addWidget(self.status_value, row, 1)

        main_layout.addWidget(card)

        content_label = QtWidgets.QLabel("Content", self)
        content_label.setProperty("variant", "fieldLabel")
        main_layout.addWidget(content_label)

        self.content_editor = QtWidgets.QTextEdit(self)
        self.content_editor.setObjectName("contentEditor")
        self.content_editor.setReadOnly(True)
        self.content_editor.setAcceptRichText(False)
        self.content_editor.setLineWrapMode(QtWidgets.QTextEdit.LineWrapMode.WidgetWidth)
        main_layout.addWidget(self.content_editor, 1)

        button_row = QtWidgets.QHBoxLayout()
        button_row.addStretch(1)

        self.copy_button = QtWidgets.QPushButton("Copy", self)
        self.copy_button.clicked.connect(self._copy_to_clipboard)
        button_row.addWidget(self.copy_button)

        self.close_button = QtWidgets.QPushButton("Close", self)
        self.close_button.setObjectName("primaryButton")
        self.close_button.clicked.connect(self.accept)
        button_row.addWidget(self.close_button)

        main_layout.addLayout(button_row)

    def _populate_fields(self) -> None:
        self.title_value.setText(str(self._message.get("title", "")))
        self.sender_value.setText(str(self._message.get("sender", "")))
        self.priority_value.setText(str(self._message.get("priority", "")))
        self.status_value.setText(str(self._message.get("status", "")))
        self.content_editor.setPlainText(str(self._message.get("content", "")))

    def _copy_to_clipboard(self) -> None:
        # Concise copy of the full details for easy pasting elsewhere.
        details = (
            f"Title: {self.title_value.text()}\n"
            f"From: {self.sender_value.text()}\n"
            f"Priority: {self.priority_value.text()}\n"
            f"Status: {self.status_value.text()}\n\n"
            f"Content:\n{self.content_editor.toPlainText()}\n"
        )
        QtGui.QGuiApplication.clipboard().setText(details)


def show_message_details(parent: Optional[QtWidgets.QWidget], item: Dict[str, Any]) -> int:
    """Convenience API to display message details as a modal dialog.

    Returns the dialog result code (e.g., QDialog.Accepted).
    """
    dialog = MessageDetailsDialog(parent=parent, message=item)
    return dialog.exec()
