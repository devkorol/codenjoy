## Как играть?

Игра пошаговая, каждую секунду сервер посылает твоему клиенту
состояние обновленного поля на текущий момент и ожидает ответа
команды герою. За следующую секунду игрок должен успеть дать
команду герою. Если не успел — герой стоит на месте.

Твоя цель заставить героя двигаться в соответствии с задуманным тобой алгоритмом.
Герой на поле должен уметь зарабатывать так много очков, как только сможет.
Основная цель игры - обыграть по очкам всех соперников.

## Команды управления

Команд несколько: 

* `UP`, `DOWN`, `LEFT`, `RIGHT` – приводят к движению героя в
  заданном направлении на 1 клетку.
* `ACT` - оставить зелье на месте героя. Также, если у героя есть перк 
  `POTION_REMOTE_CONTROL` - он может взорвать свое зелье дистанционно 
  по второй команде `ACT` тогда, когда ему это потребуется.
* `АСТ,<DIRECTION>`,`<DIRECTION>,АСТ` - команды движения можно
  комбинировать с командой `ACT`, разделяя их через запятую. Порядок 
  `LEFT,ACT` или `ACT,LEFT` - имеет значение, либо двигаемся 
  влево и там ставим зелье, либо ставим зелье, а затем ходим
  влево. Если игрок будет использовать только одну команду `ACT`, то зелье
  установится под героем без его перемещения на поле. 
* `АСТ(1),<DIRECTION>` - Используется только при наличии перка 
  `POISON_THROWER`. Позволяет бросить в сторону противника 
  пары яда. Используется в паре с командой смены направления 
  движения, разделенные через запятую. Порядок
  `LEFT,ACT(1)` или `ACT(1),LEFT` - значения не имеет. Без указания 
  направления ничего не произойдет, герой останется стоять на месте.
* `ACT(2)` - работает только с перком `POTION_EXPLODER`. После вызова 
  команды все зелья на поле взрываются одновременно. Действует на все 
  зелья (собственные, командные, вражеские, дистанционные).
  Может использоваться как одна команда и может сочетаться с направлением.
  Пример: `RIGHT,ACT(2)` - в этом случае Молли попытается двигаться 
  вправо, после чего все зелья на поле взорвутся.

## Перки

* `POTION_BLAST_RADIUS_INCREASE` - увеличивает радиус взрыва зелья.
  `{значение: +2, время действия: 30}`[(?)](#ask)
* `POTION_COUNT_INCREASE` - временно увеличивает количество устанавливаемых зелий.
  `{количество: +4, время действия: 30}`[(?)](#ask)
* `POTION_REMOTE_CONTROL` - несколько следующих зейли будут управляемым таймером.
  Активация командой `ACT`. `{значение:  3}`[(?)](#ask)
* `POTION_IMMUNE` - дает временную неуязвимость к взрыву.
  `{время действия: 30}`[(?)](#ask)
* `POISON_THROWER`  герой может стрелять облаком яда.
  Используется командой: `ACT(1),<DIRECTION>`. `{время действия: 30}`[(?)](#ask)
* `POTION_EXPLODER`  Герой может взровать одновременно все зелья на поле.
  Используется командой: `ACT(2)`. `{number of  use: +1, время действия: 30}`[(?)](#ask)

## Настройки

Параметры будут меняться[(?)](#ask) по ходу игры. Значения по-умолчанию
представлены в таблице ниже:

| Событие | Название | Очки |
|--------|--------|--------|
| Очки за открытый сундук | OPEN_TREASURE_BOX_SCORE | 1[(?)](#ask) |  
| Очки за уничтожение призрака | KILL_GHOST_SCORE | 10[(?)](#ask) |  
| Очки за уничтожение героя | KILL_OTHER_HERO_SCORE | 20[(?)](#ask) |  
| Очки за уничтожение вражеского героя | KILL_ENEMY_HERO_SCORE | 100[(?)](#ask) |  
| Очки за подобранный перк | CATCH_PERK_SCORE | 5[(?)](#ask) |  
| Штрафные очки за потерю своего героя | HERO_DIED_PENALTY | -30[(?)](#ask) |  
| Очки за победу в раунде | WIN_ROUND_SCORE | 30[(?)](#ask) |  
| Большой бадабум | BIG_BADABOOM | false[(?)](#ask) |  
| Количество зелья | POTIONS_COUNT | 1[(?)](#ask) |  
| Сила зелья | POTION_POWER | 3[(?)](#ask) |  
| Количество сундуков с сокровищами | TREASURE_BOX_COUNT | 52[(?)](#ask) |  
| Количество призраков | GHOSTS_COUNT | 5[(?)](#ask) |  
| Все товарищи по команде получают бонус за перк | PERK_WHOLE_TEAM_GET | false[(?)](#ask) |  
| Коэффициент выпадения перков в % | PERK_DROP_RATIO | 20[(?)](#ask) |  
| Таймаут перков | PERK_PICK_TIMEOUT | 30[(?)](#ask) |  
| Увеличение радиуса взрыва зелья | PERK_POTION_BLAST_RADIUS_INC | 2[(?)](#ask) |  
| Таймаут эффекта увеличения радиуса взрыва зелья | TIMEOUT_POTION_BLAST_RADIUS_INC | 30[(?)](#ask) |  
| Увеличение количества зелий | PERK_POTION_COUNT_INC | 4[(?)](#ask) |  
| Таймаут эффекта увеличение количества зелий | TIMEOUT_POTION_COUNT_INC | 30[(?)](#ask) |  
| Таймаут эффекта иммунитета к зельям | TIMEOUT_POTION_IMMUNE | 30[(?)](#ask) |  
| Таймаут эффекта метателя яда | TIMEOUT_POISON_THROWER | 30[(?)](#ask) |  
| Таймаут эффекта взрыва зелий | TIMEOUT_POTION_EXPLODER | 30[(?)](#ask) |  
| Перезарядка метателя яда | POISON_THROWER_RECHARGE | 3[(?)](#ask) |  
| Количество пультов управления Зельями (сколько раз игрок может их использовать) | REMOTE_CONTROL_COUNT | 3[(?)](#ask) |  
| Количество взрывателей зелий (сколько раз игрок может их использовать) | POTION_EXPLODER_COUNT | 1[(?)](#ask) |  
| Украсть очки у владельца зелья (работает с перком Potion Exploder) | STEAL_POINTS | false[(?)](#ask) |  
| Перки доступные в этой игре | DEFAULT_PERKS | ''[(?)](#ask) |  
| Режим подсчета очков | SCORE_COUNTING_TYPE | 0 (0 - Простой инкремент очков, 1 - Максимальное количество очков в комнате, 2 - Максимальное количество очков в серии между смертями героя)[(?)](#ask) |

## Кейзы

* Ты можешь комбинировать перки
* Кто получит очки после использования `POTION_EXPLODER` - решает Сенсей[(?)](#ask).
* Пожалуйста, будь осторожен с перками на поле.

## Подсказки

Первостепенная задача – написать websocket клиента, который подключится
к серверу. Затем заставить героя на поле слушаться команд.
Таким образом, игрок подготовится к основной игре.
Основная цель – вести осмысленную игру и победить.

Если ты не знаешь с чего начать, попробуй реализовать следующие алгоритмы:

* Перейти в случайную пустую соседнюю ячейку.
* Продвинуться вперед в свободную клетку в направлении ближайшего сундука.
* Попробуй взорвать что-то зельем.
* Попробуй спрятаться от взрывной волны.
* Попробуй избежать привидений и других героев.