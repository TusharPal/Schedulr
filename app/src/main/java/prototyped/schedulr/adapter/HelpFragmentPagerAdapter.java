package prototyped.schedulr.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import prototyped.schedulr.activity.FragmentHelpSlide;

public class HelpFragmentPagerAdapter extends FragmentPagerAdapter
{
    private final int fragmentCount = 3;
    private Context context;

    public HelpFragmentPagerAdapter(Context context, FragmentManager fragmentManager)
    {
        super(fragmentManager);

        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        FragmentHelpSlide fragmentHelpSlide = FragmentHelpSlide.newInstance(context, position);

        return fragmentHelpSlide;
    }

    @Override
    public int getCount()
    {
        return fragmentCount;
    }
}
