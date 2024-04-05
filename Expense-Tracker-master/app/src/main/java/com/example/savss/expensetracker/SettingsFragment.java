// Import required libraries

// Define SettingsFragment class
public class SettingsFragment extends Fragment {

    // Override onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain instance of the fragment
    }

    // Override onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabs, container, false);

        // Find ViewPager from layout
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Setup ViewPager
        setupViewPager(viewPager);

        // Find TabLayout from layout
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);

        // Setup tabs with ViewPager
        tabs.setupWithViewPager(viewPager);

        // Return the view
        return view;
    }

    // Method to setup ViewPager
    private void setupViewPager(ViewPager viewPager) {
        // Create adapter for ViewPager
        Adapter adapter = new Adapter(getChildFragmentManager());

        // Add fragments to adapter
        adapter.addFragment(new ProfileManagement(), "Profile Management");
        adapter.addFragment(new AppSettings(), "App Settings");

        // Set adapter to ViewPager
        viewPager.setAdapter(adapter);
    }

    // Define Adapter class for ViewPager
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        // Constructor
        public Adapter(FragmentManager manager) {
            super(manager);
        }

        // Override getItem method
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        // Override getCount method
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        // Method to add fragment to adapter
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        // Override getPageTitle method
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
