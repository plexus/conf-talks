````dot
graph lisa {
  LISA[shape="none"  weight=11];
  WRITE[shape="none"];
  GOOD[shape="none"];
  CODE[shape="none"];
  s[shape="circle" weight=10];
  p[shape="circle" weight=10];
  m[shape="circle"];
  s -- LISA
  s -- p
  p -- WRITE
  p -- m
  m -- GOOD;
  m -- CODE;
}
````
