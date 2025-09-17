import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import { Job, JobSearchParams, JobCategory } from '../../types';
import { jobService, jobCategoryService } from '../../services/jobs';

// 状态接口
interface JobState {
  // 职位列表
  jobs: Job[];
  totalJobs: number;
  currentPage: number;
  totalPages: number;
  loading: boolean;
  
  // 当前职位详情
  currentJob: Job | null;
  jobLoading: boolean;
  
  // 搜索参数
  searchParams: JobSearchParams;
  
  // 职位分类
  categories: JobCategory[];
  categoriesLoading: boolean;
  
  // 热门职位
  hotJobs: Job[];
  hotJobsLoading: boolean;
  
  // 最新职位
  latestJobs: Job[];
  latestJobsLoading: boolean;
  
  // 精选职位
  featuredJobs: Job[];
  featuredJobsLoading: boolean;
  
  // 相关职位
  relatedJobs: Job[];
  relatedJobsLoading: boolean;
  
  // 错误信息
  error: string | null;
}

// 初始状态
const initialState: JobState = {
  jobs: [],
  totalJobs: 0,
  currentPage: 0,
  totalPages: 0,
  loading: false,
  
  currentJob: null,
  jobLoading: false,
  
  searchParams: {
    page: 0,
    size: 20,
    sort: 'createdAt',
    direction: 'desc',
  },
  
  categories: [],
  categoriesLoading: false,
  
  hotJobs: [],
  hotJobsLoading: false,
  
  latestJobs: [],
  latestJobsLoading: false,
  
  featuredJobs: [],
  featuredJobsLoading: false,
  
  relatedJobs: [],
  relatedJobsLoading: false,
  
  error: null,
};

// 异步actions
export const fetchJobsAsync = createAsyncThunk(
  'job/fetchJobs',
  async (params: Partial<JobSearchParams>, { rejectWithValue }) => {
    try {
      const response = await jobService.getJobs({ ...initialState.searchParams, ...params });
      if (response.success) {
        return { data: response.data, params };
      } else {
        return rejectWithValue(response.message || '获取职位列表失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取职位列表失败');
    }
  }
);

export const fetchJobByIdAsync = createAsyncThunk(
  'job/fetchJobById',
  async (id: number, { rejectWithValue }) => {
    try {
      const response = await jobService.getJobById(id);
      if (response.success) {
        // 增加浏览量
        jobService.incrementJobView(id).catch(() => {
          // Silently handle view increment errors
        });
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取职位详情失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取职位详情失败');
    }
  }
);

export const fetchCategoriesAsync = createAsyncThunk(
  'job/fetchCategories',
  async (_, { rejectWithValue }) => {
    try {
      const response = await jobCategoryService.getCategories();
      if (response.success) {
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取职位分类失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取职位分类失败');
    }
  }
);

export const fetchHotJobsAsync = createAsyncThunk(
  'job/fetchHotJobs',
  async (limit: number = 10, { rejectWithValue }) => {
    try {
      const response = await jobService.getHotJobs(limit);
      if (response.success) {
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取热门职位失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取热门职位失败');
    }
  }
);

export const fetchLatestJobsAsync = createAsyncThunk(
  'job/fetchLatestJobs',
  async (limit: number = 10, { rejectWithValue }) => {
    try {
      const response = await jobService.getLatestJobs(limit);
      if (response.success) {
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取最新职位失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取最新职位失败');
    }
  }
);

export const fetchFeaturedJobsAsync = createAsyncThunk(
  'job/fetchFeaturedJobs',
  async (limit: number = 10, { rejectWithValue }) => {
    try {
      const response = await jobService.getFeaturedJobs(limit);
      if (response.success) {
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取精选职位失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取精选职位失败');
    }
  }
);

export const fetchRelatedJobsAsync = createAsyncThunk(
  'job/fetchRelatedJobs',
  async ({ jobId, limit = 5 }: { jobId: number; limit?: number }, { rejectWithValue }) => {
    try {
      const response = await jobService.getRelatedJobs(jobId, limit);
      if (response.success) {
        return response.data;
      } else {
        return rejectWithValue(response.message || '获取相关职位失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '获取相关职位失败');
    }
  }
);

export const searchJobsAsync = createAsyncThunk(
  'job/searchJobs',
  async ({ keyword, ...params }: { keyword: string } & Partial<JobSearchParams>, { rejectWithValue }) => {
    try {
      const response = await jobService.searchJobs(keyword, params);
      if (response.success) {
        return { data: response.data, params: { keyword, ...params } };
      } else {
        return rejectWithValue(response.message || '搜索职位失败');
      }
    } catch (error: any) {
      return rejectWithValue(error.message || '搜索职位失败');
    }
  }
);

// 创建slice
const jobSlice = createSlice({
  name: 'job',
  initialState,
  reducers: {
    // 清除错误
    clearError: (state) => {
      state.error = null;
    },
    
    // 更新搜索参数
    updateSearchParams: (state, action: PayloadAction<Partial<JobSearchParams>>) => {
      state.searchParams = { ...state.searchParams, ...action.payload };
    },
    
    // 重置搜索参数
    resetSearchParams: (state) => {
      state.searchParams = initialState.searchParams;
    },
    
    // 清除当前职位
    clearCurrentJob: (state) => {
      state.currentJob = null;
    },
    
    // 清除职位列表
    clearJobs: (state) => {
      state.jobs = [];
      state.totalJobs = 0;
      state.currentPage = 0;
      state.totalPages = 0;
    },
  },
  extraReducers: (builder) => {
    // 获取职位列表
    builder
      .addCase(fetchJobsAsync.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchJobsAsync.fulfilled, (state, action) => {
        state.loading = false;
        state.jobs = action.payload.data.content;
        state.totalJobs = action.payload.data.totalElements;
        state.currentPage = action.payload.data.number;
        state.totalPages = action.payload.data.totalPages;
        state.searchParams = { ...state.searchParams, ...action.payload.params };
      })
      .addCase(fetchJobsAsync.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });

    // 获取职位详情
    builder
      .addCase(fetchJobByIdAsync.pending, (state) => {
        state.jobLoading = true;
        state.error = null;
      })
      .addCase(fetchJobByIdAsync.fulfilled, (state, action) => {
        state.jobLoading = false;
        state.currentJob = action.payload;
      })
      .addCase(fetchJobByIdAsync.rejected, (state, action) => {
        state.jobLoading = false;
        state.error = action.payload as string;
      });

    // 获取职位分类
    builder
      .addCase(fetchCategoriesAsync.pending, (state) => {
        state.categoriesLoading = true;
      })
      .addCase(fetchCategoriesAsync.fulfilled, (state, action) => {
        state.categoriesLoading = false;
        state.categories = action.payload;
      })
      .addCase(fetchCategoriesAsync.rejected, (state, action) => {
        state.categoriesLoading = false;
        state.error = action.payload as string;
      });

    // 获取热门职位
    builder
      .addCase(fetchHotJobsAsync.pending, (state) => {
        state.hotJobsLoading = true;
      })
      .addCase(fetchHotJobsAsync.fulfilled, (state, action) => {
        state.hotJobsLoading = false;
        state.hotJobs = action.payload;
      })
      .addCase(fetchHotJobsAsync.rejected, (state, action) => {
        state.hotJobsLoading = false;
        state.error = action.payload as string;
      });

    // 获取最新职位
    builder
      .addCase(fetchLatestJobsAsync.pending, (state) => {
        state.latestJobsLoading = true;
      })
      .addCase(fetchLatestJobsAsync.fulfilled, (state, action) => {
        state.latestJobsLoading = false;
        state.latestJobs = action.payload;
      })
      .addCase(fetchLatestJobsAsync.rejected, (state, action) => {
        state.latestJobsLoading = false;
        state.error = action.payload as string;
      });

    // 获取精选职位
    builder
      .addCase(fetchFeaturedJobsAsync.pending, (state) => {
        state.featuredJobsLoading = true;
      })
      .addCase(fetchFeaturedJobsAsync.fulfilled, (state, action) => {
        state.featuredJobsLoading = false;
        state.featuredJobs = action.payload;
      })
      .addCase(fetchFeaturedJobsAsync.rejected, (state, action) => {
        state.featuredJobsLoading = false;
        state.error = action.payload as string;
      });

    // 获取相关职位
    builder
      .addCase(fetchRelatedJobsAsync.pending, (state) => {
        state.relatedJobsLoading = true;
      })
      .addCase(fetchRelatedJobsAsync.fulfilled, (state, action) => {
        state.relatedJobsLoading = false;
        state.relatedJobs = action.payload;
      })
      .addCase(fetchRelatedJobsAsync.rejected, (state, action) => {
        state.relatedJobsLoading = false;
        state.error = action.payload as string;
      });

    // 搜索职位
    builder
      .addCase(searchJobsAsync.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(searchJobsAsync.fulfilled, (state, action) => {
        state.loading = false;
        state.jobs = action.payload.data.content;
        state.totalJobs = action.payload.data.totalElements;
        state.currentPage = action.payload.data.number;
        state.totalPages = action.payload.data.totalPages;
        state.searchParams = { ...state.searchParams, ...action.payload.params };
      })
      .addCase(searchJobsAsync.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

// 导出actions
export const { 
  clearError, 
  updateSearchParams, 
  resetSearchParams, 
  clearCurrentJob, 
  clearJobs 
} = jobSlice.actions;

// 选择器
export const selectJobs = (state: { job: JobState }) => state.job.jobs;
export const selectJobsLoading = (state: { job: JobState }) => state.job.loading;
export const selectCurrentJob = (state: { job: JobState }) => state.job.currentJob;
export const selectJobLoading = (state: { job: JobState }) => state.job.jobLoading;
export const selectSearchParams = (state: { job: JobState }) => state.job.searchParams;
export const selectCategories = (state: { job: JobState }) => state.job.categories;
export const selectHotJobs = (state: { job: JobState }) => state.job.hotJobs;
export const selectLatestJobs = (state: { job: JobState }) => state.job.latestJobs;
export const selectFeaturedJobs = (state: { job: JobState }) => state.job.featuredJobs;
export const selectRelatedJobs = (state: { job: JobState }) => state.job.relatedJobs;
export const selectJobError = (state: { job: JobState }) => state.job.error;

export default jobSlice.reducer;
