MainActivity
│
└── DrawerLayout
├── ConstraintLayout (main content)
│   ├── AppBarLayout
│   │   └── MaterialToolbar
│   │       └── SearchBar
│   └── NavHostFragment
│       └── Current Fragment
│
└── NavigationView
├── Menu Item 1
├── Menu Item 2
└── ...

Click
↓
ViewModel changes selectedDate
↓
Generate 35 new DayCell objects
↓
LiveData emits
↓
ListAdapter + DiffUtil compares lists
↓
RecyclerView updates