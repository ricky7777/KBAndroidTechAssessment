# KB Android Technical Assessment
# Overview
● This test simulates a hybrid situation between the old and new architecture. When using the new architecture (Jetpack Compose), the traditional architecture (BottomSheetDialog) is used within the DatePickerDialog. Additionally, the new architecture (MaterialDatePicker) is invoked upon clicking inside the DatePickerDialog.

● The Repository Design Pattern is employed to separate the Model layer, allowing for future data compatibility and scalability. 

● The TransactionViewModel serves as the main logic control layer, providing the core logic for the front-end UI.

## Architecture
<img width="800" alt="architecture" src="https://github.com/user-attachments/assets/f6dbbcc5-0d29-4e0a-9563-2914620bfb0d">

## Demo
<img width="200" src="https://github.com/user-attachments/assets/9b2f34e1-80b5-4df3-8e10-64e36fc2bae4"><br/><br/>

<h1>Unit test</h1>
<img width="965" alt="unit test" src="https://github.com/user-attachments/assets/5fc56578-2472-4e88-afb6-f9a174c70153">

<h1>Use Skill</h1>
1. Hybrid Architecture(Jetpack Compose/traditional layout, widget/MVVM)<br/>
2. MVVM<br/>
3. Jetpack Compose<br/>
2. ViewBinding<br/>
3. Coroutine<br/>
5. Repository pattern<br/>
7. Unit test(JUnit/Mockito)
<br/><br/>

# Task

Implement the following functionalities:

- Calculate Balance: Accurately calculate and display the available balance based on the
  transactions.
  ```diff
  + Use TransactionViewModel to control and observe balance and in TxDetailScreen collectAsState balance
  ```
- Date Filter: Add a date filter UI element (e.g., date pickers or text fields).
  ```diff
  + Use MaterialDatePicker for date pickers and provide extra three shortcut way to filter quickly.
  ```
- Filter Transactions: When a filter is applied, update the transaction list to display only
  transactions within the specified date range.
  ```diff
  + Use StateFlow to keep filteredTransactions and on the compose side use filteredTransactions.collectAsState().
  ```
  
- Update Balance: As transactions are filtered, recalculate and display the filtered balance.
  ```diff
  + Use StateFlow to keep totalBalance and on the compose side use totalBalance.collectAsState().
  ```
  
- Reset Filter: Provide a way to reset the filters and view all transactions.
  ```diff
  + Provide a reset icon on topbar.
  + When clicking the reset button, call transactionViewModel reset balance.
  ```

<h1>TBD Enhancment</h1><br/>

● Bug tracking system(Firebase Crashlytics)

● Google analytics interfacing

● Network data provide interfacing
