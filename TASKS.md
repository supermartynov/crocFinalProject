# Задачи

Всем задачам мы назначили сложность: 
- `A` сложно 
- `B` очень сложно 
- `C` крайне сложно
  
и расписали состав функций, над которыми ожидаем, что вы поработаете. Каждую из функций можем отдельно прорабатывать и уточнять вместе с вами в процессе.

Мы ожидаем, что вы примените в процессе работы над проектом знания, которые получили в рамках курса и что код будет структурированным: отдельные модули и алгоритмы вынесены в классы/пакеты.

## Бот `A`

Задача бота - шагать по карте. Есть пример реализации starter-bot, с которого начинаете разработку. Вам предстоит реализовать и комбинировать следующие функции для принятия решения о ходе.

- Исследование карты. Бот должен стремиться собрать информацию о карте и посетить участки, которые он еще “не видел”.

- Обход препятствий. Алгоритм обхода может быть как простым, например, поворачивать в случайном направлении при достижении заблокированной ячейки, так и более сложным (A* или поиск в ширину).

- Сбор монет. При принятии решения, к какой монете должен идти бот, могут учитываться разные факторы, например, расстояние до нее, наличие рядом с ней других ботов и других монет.

- (опционально) DEATMATCH: убегать от опасных ботов и охотиться на слабых.

## Генератор карт `B`

Генератор на вход принимает параметры карты и формирует на выходе один или несколько файлов карт в описанном формате. Параметры: ширина, высота, количество ботов-участников, количество препятствий на карте: низкое/среднее/высокое.

- Алгоритм генерации. Необходимо выбрать и реализовать один или несколько алгоритмов задания препятствий. лабиринт, симуляция реального ландшафта, свой вариант.

- Подбор подходящих параметров view/mining/attack радиусов. Для указанного размера и алгоритма генерации подобрать способ вычисления перечисленных параметров для каждой карты.

- Автоматический выбор стартовых позиций ботов на карте.
  
- (опционально) Симметричность карты. Обеспечить симметричность карт для равенства начальных условий для игроков.

## Консольный редактор карт `A`

Редактор на вход принимает путь к файлу карты, выводит ее на экран, позволяет изменять через команды пользователя и сохранять результат.

- Загрузка и отображение карты.

- Редактирование: изменение размеров, заблокированных ячеек, начальных позиций ботов.

- Редактирование параметров карты: view/mining/attack радиусов.

- (опционально) Вместо консольного реализовать графический редактор на JavaFX (с этим пунктом сложность существенно выше, плюс придется самостоятельно  разобраться с JavaFX)

## Графический визуализатор матчей `C`

Визуализатор (или плеер) на вход получает путь к файлу лога матча и проигрывает анимацию матча по раундам.

- Отображение карты, объектов на ней и событий матча (появление/сбор монет, атаки, перемещения ботов).

- Вывод названий и текущих весов ботов (количества собранных монет).

- (опционально) Перемотка (слайдер) и возможность поставить анимацию на паузу.

## Статистика и рейтинг `C`

Приложение анализирует накопленные логи матчей и формирует отчет с различными статистическими параметрами и рейтингом ботов.

- Список матчей с названиями ботов участников и результатами.

- Таблица общего рейтинга ботов.

- Статистика с метриками по каждому боту: например, средний процент исследования карты, процент побед в атаках, скорость/эффективность сбора монет и т.п.

- (опционально) Отдельные рейтинг ботов по каждой карте.