concurrent-player
=================

####[SynchronizedMethodPlayer](/core/src/main/java/me/sbozhko/test/SynchronizedMethodPlayer.java)

Классическая реализация. Методы доступа и изменения полей синхронизированы. С точки зрения производительности этот код будет не очень хорошим. Да и нет возможности попытаться получить блокировку с неудачным исходом или попытаться получить блокировку в течение некоторого промежутка времени с последующим отказом. 

####[SynchronizedBlockPlayer](/core/src/main/java/me/sbozhko/test/SynchronizedBlockPlayer.java)

Синхонизация идет уже в блоке. Для этого использован приватный объект lock. Т.к. синхронизирован только блок, то эта имплементация будет быстрее, не намного, но все же быстрее. И если нам понадобится какая-то особенная логика, то синхронизация блоками дает более гибкий мезанизм. Ведь можно выбрать, на каком обекте будем лочиться. Хотя интересное дело. Видела где-то в интернетах, статью, когда показывали, какой asm-код генерится в случае с synchronized-метода и synchronized-блока. Так вот, код, который был после synchronized-метода был в раза 3 короче. Правда, там и блокировка в synchronized-блоке была на this, а не на отдельном объекте.

####[ReentrantLockedPlayer](/core/src/main/java/me/sbozhko/test/ReentrantLockedPlayer.java)

Имплементация с использованием обычного ReentrantLock. Явное использование объекта Lock. Плюсом будет то, есть возможность более гибкого использования локов. Всегда можно сохранить корректное состояние системы в секции finally. Можно попытаться взять блокировку с неудачным исходом. Так что все более гибко. По скорости я ожидала, что это будет быстрее, чем synchronized, но в итоге получилось, что это не так.

####[ReadWriteLockedPlayer](/corek/src/main/java/me/sbozhko/test/ReadWriteLockedPlayer.java)

Использование отдельных локов для чтения и для записи. Обычно получается более производительное решение, нежели с обычным локом. Но в итоге вышло примерно так же.

####[AtomicsPlayer](/core/src/main/java/me/sbozhko/test/AtomicsPlayer.java)

Используются атомики для хранения и данных об очках и карме. Синхронизирован лишь метод update, потому как нужно одновременно обновлять оба поля. Синхронизировать getPoints и getKudos не нужно, так как корректное поведение обеспечивают атомики. Эта имплементация работает наиболее быстро.

####[StmPlayer](/core/src/main/java/me/sbozhko/test/StmPlayer.java)

Имплементация интерфейса, которая базируется на Scala STM. Идея программной транзакционной памяти не использует блокировки как таковые. Она придерживается оптимистической концепции при совместном использовании разделяемых ресурсов. Поэтому предполагала, что этот вариант будет самым быстрым, но нет. По производительности получлось чуть лучше ReadWriteLockedPlayer.

####Каким образом проверялась производительность?
Добавила в имплеменацию Player еще одно поле AtomicLong — счетчик вызовов. При каждом вызове методов интерфейса этот счетчик инкрементился. Запускала несколько (5) потоков, которые дергали эти методы, и один, который вызывался через 5 секунд и показывал, каково значение счетчика. Чем больше значение, тем более производительное решение.

Производительность в порядке убывания: 
* AtomicsPlayer
* SynchronizedBlockPlayer
* SynchronizedMethodPlayer
* ReentrantLockedPlayer
* StmPlayer
* ReadWriteLockedPlayer

