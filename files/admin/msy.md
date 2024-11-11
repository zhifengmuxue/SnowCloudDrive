论文复现



1 环境安装

```
conda create -n ElDet python=3.6
conda activate ElDet
```

2 依赖安装

```
conda install pytorch=1.7.0 torchvision cudatoolkit=11.0 -c pytorch
pip install -r requirements.txt
```

修改requirements.txt:

```
opencv-python==4.5.3.56
Cython==0.29.24
numpy==1.19.5
future==1.0.0
numba==0.53.1
progress==1.5
matplotlib==3.3.4
easydict==1.9
scipy==1.5.4
tensorboardX==2.1
tqdm==4.64.0
pycocotools==2.0.2
protobuf==3.19.6
```

3 DCNv2

注意这里sh脚本里面的是linux指令，如果再win上跑，需要使用一下工具如git bash

```
cd DCNv2
sh make.sh
```

复制编译完成的文件到主目录下，改名为dcn
