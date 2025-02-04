```mermaid
graph TD
  %% Actors %%
  User(["🧑 User"])
  FileSystem(["💾 File System"])

  %% Use Cases %%
  UC1["🟢 Start JabberPoint"]
  UC2["📂 Load Presentation"]
  UC3["💾 Save Presentation"]
  UC4["📑 View Slide"]
  UC5["⬅️ Navigate Slides"]
  UC6["🎨 Display Slide Content"]
  UC7["🔄 Update Slide"]
  UC8["🔍 Select Slide"]
  UC9["⏩ Next Page"]
  UC10["⏪ Previous Page"]

  %% Relationships %%
  User --> UC1
  User --> UC2
  User --> UC3
  User --> UC4
  User --> UC5
  User --> UC8

  UC2 -->|Reads file| FileSystem
  UC3 -->|Writes file| FileSystem

  UC5 --> UC9
  UC5 --> UC10
  UC4 --> UC6
  UC6 --> UC7

  UC8 -->> UC4
  UC7 -->> UC3
  ```
