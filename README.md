# RenderDragon2Source
A source code of Render Dragon by PandaMine5
Works on MCBE 1.21.70 - 1.21.100+ 

Requires: 
- Windows 11
- Python 3.12 + 
- Vs Code for editing and running   commands

## ⚡ Quick Start

1. **Install Python 3.12+**  
   - Download from [python.org](https://www.python.org/downloads/).


2. **Install Lazurite**  
   - Open a terminal (Command Prompt or VS Code terminal).
   - Run:  
     ```sh
     pip install lazurite
     ```

3. **Click Code -> Download ZIP**
   - Then unpack zip file and open it in VS Code 


## 🛠️ Compile Materials

To compile materials using **Lazurite**, run the following command:


```bash

lazurite build ./proj -p <android | ios | windows>

```

You can use ``windows_compact`` to compile on latest DirectX 12
or
``android_compact`` to compile on for phones 2018+